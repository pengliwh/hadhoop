package com.chinacscs.platform.search.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chinacscs.platform.commons.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: liusong
 * @date: 2019年1月12日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@Component("IndexForEsWorker")
@Slf4j
public class IndexForEsWorker implements StressTestWorker {

	@Value("${app.home}")
	private String appHome;

	@Value("${es.url}")
	private String esUrl;

	@Value("${export.table}")
	private String exportTable;

	@Value("${import.index}")
	private String importIndex;

	@Value("${export.table}")
	private String table;

	@Value("${index.batch.size:5}")
	private int indexBatchSize;

	private Map<String, String> fields;

	private volatile static Queue<File> fileQueue;

	private String dataDirPath;

	private static File errDir;

	private File errFile;

	private int errFileMaxSize = 1000;

	private int errFileSize;

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	private synchronized static File initErrFile(String dataDirPath, String table) {
		errDir = new File(dataDirPath, table + "_err");
		File errFile = null;
		if (!errDir.exists()) {
			errDir.mkdir();
			errFile = new File(errDir, "1");
		} else {
			int curIndex = errDir.list().length + 1;
			errFile = new File(errDir, String.valueOf(curIndex));
		}
		return errFile;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		dataDirPath = appHome + File.separatorChar + "data";
		fields = new LinkedHashMap<>();
		fields.put("id", "id");// --公司id
		fields.put("code", "code");// 公司编码
		fields.put("name", "company_nm");// 公司名不分词
		fields.put("stock_code", "security_cd");// 股票代码
		fields.put("stock_name", "security_snm");// 股票名称
		fields.put("legal_person_name", "legal_person_nm");// 法人名称
		fields.put("hairman_name", "chairman");// 董事长名称
		fields.put("general_manager_name", "gmanager");// 总经理名称
		fields.put("type", "company_type");// 类型
		fields.put("industry", "industry");// 行业
		fields.put("status", "company_st");// 状态
		fields.put("found_date", "found_dt");// 创建日期
		fields.put("register_capital", "regcapital");// 注册资本
		fields.put("register_region", "reg_region");// 注册区域
		fields.put("email", "email");// 邮箱
		fields.put("phone", "phone");// 电话
		fields.put("address", "address");// 地址
		fields.put("business_scope", "business_scope");// 业务范围
	}

	private synchronized void initFileQueue(String dataDir) {
		if (null == fileQueue) {
			fileQueue = new LinkedBlockingQueue<>();
			File dataDirFile = new File(dataDir);
			File[] dataFiles = dataDirFile.listFiles();
			for (File dataFile : dataFiles) {
				fileQueue.add(dataFile);
			}
		}
	}

	@Override
	public final void run(String job, String group) {
		String dataDir = dataDirPath + File.separatorChar + table;
		initFileQueue(dataDir);
		File dataFile = null;
		List<String> batchList = new ArrayList<>(indexBatchSize);
		while (null != (dataFile = fileQueue.poll())) {
			try {
				LineIterator LineIterator = FileUtils.lineIterator(dataFile, "utf-8");
				LineIterator.forEachRemaining(dataJson -> {
					batchList.add(dataJson);
					if (batchList.size() >= indexBatchSize) {
						consume(batchList);
						batchList.clear();
					}
				});
				LineIterator.close();
				if (batchList.size() > 0) {
					consume(batchList);
				}
			} catch (Exception exception) {
				log.error("produce data error", exception);
				throw new RuntimeException(exception);
			}
		}

	}

	private void consume(List<String> dataJsons) {
		BulkRequest bulkRequest = new BulkRequest();
		for (String dataJson : dataJsons) {
			try {
				Map<String, Object> data = JsonUtils.jsonToMap(dataJson);
				Map<String, Object> newData = new HashMap<String, Object>(fields.size());
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					Object value = data.get(entry.getValue());
					if (null != value) {
						if ("found_dt".equals(entry.getValue())) {
							long timestamp = 0;
							try {
								timestamp = Long.valueOf(value.toString());
							} catch (Exception exception) {
								log.error(String.format("paser found_dt[%s] error", value),
										exception);
								String text = StringUtils.replace(value.toString(), "T", " ");
								text = StringUtils.replace(text, "Z", "");
								Date date = DateUtils.parseDate(text,
										new String[]{"yyyy-MM-dd hh:mm:ss"});
								timestamp = date.getTime();
							}
							Date foundDate = new Date(timestamp);
							//1994-00-28
							value = DateFormatUtils.format(foundDate, "yyyy-mm-dd");
						}
						newData.put(entry.getKey(), value);
					}
				}
				IndexRequest indexRequest = new IndexRequest(importIndex, importIndex);
				indexRequest.source(JsonUtils.toJsonString(newData), XContentType.JSON);
				bulkRequest.add(indexRequest);
			} catch (Exception exception) {
				handleErr(Arrays.asList(dataJson));
				log.error("paser json error", exception);
			}
		}
		try {
			BulkResponse bulkResponse=restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			BulkItemResponse[] items=bulkResponse.getItems();
			for(BulkItemResponse item:items) {
				if(item.isFailed()) {
					
				}
			}
		} catch (Exception exception) {
			handleErr(dataJsons);
			log.error("send batch index error", exception);
		}
	}
	private File getErrFile(int needSize) {
		errFileSize += needSize;
		if (null == errFile || errFileSize > errFileMaxSize) {
			errFile = initErrFile(dataDirPath, exportTable);
			errFileSize = needSize;
		}
		return errFile;
	}

	private void handleErr(List<String> errDataJson) {
		try {
			FileUtils.writeLines(getErrFile(errDataJson.size()), errDataJson, true);
		} catch (IOException exception) {
			log.error("write errfile error", exception);
		}
	}

	@PreDestroy
	public void destroy() throws IOException {
		if (null != restHighLevelClient) {
			restHighLevelClient.close();
		}
	}
}
