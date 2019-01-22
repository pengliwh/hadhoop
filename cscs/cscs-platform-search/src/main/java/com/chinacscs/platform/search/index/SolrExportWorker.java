//package com.chinacscs.platform.search.index;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.http.client.HttpClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrQuery.ORDER;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.params.CursorMarkParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.stereotype.Component;
//
//import com.chinacscs.platform.commons.util.JsonUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author: liusong
// * @date: 2019年1月11日
// * @email: 359852326@qq.com
// * @version:
// * @describe: //TODO
// */
//@Slf4j
//@Component("SolrExportWorker")
//public class SolrExportWorker extends AbstactWorker {
//
//	@Value("${app.home}")
//	private String appHome;
//
//	@Value("${export.table}")
//	private String exportTable;
//
//	@Value("${proxy.enable}")
//	private boolean proxyEnable;
//
//	@Value("${batch.size:10000}")
//	private int batchSize;
//
//	@Value("${file.max.size:1000000}")
//	private int fileMaxSize;
//
//	@Autowired
//	private HttpClient httpClient;
//
//	@Autowired
//	private ConfigurableApplicationContext applicationContext;
//
//	private String dataDir;
//
//	private int fileIndex;
//
//	private int fileSize;
//
//	private File file;
//
//	@PostConstruct
//	private void init() {
//		this.dataDir = getDataPath();
//	}
//
//	@Override
//	public void run(String job,String group){
//		log.info("appHome:" + appHome);
//		log.info("exportTable:" + exportTable);
//		log.info("proxyEnable:" + proxyEnable);
//		log.info("dataDir:" + dataDir);
//		try {
//			String url = "http://10.100.48.35:8983/solr/" + exportTable;
//			HttpSolrClient.Builder solrBuilder=new Builder();
//			solrBuilder.withBaseSolrUrl(url);
//			solrBuilder.withHttpClient(httpClient);
//			HttpSolrClient solrClient =solrBuilder.build();
//			SolrQuery solrQuery = new SolrQuery();
//			solrQuery.setQuery("*:*");
//			solrQuery.setFields("*");
//			solrQuery.addSort("id", ORDER.asc);
//			String cursorMark = CursorMarkParams.CURSOR_MARK_START;
//			solrQuery.setRows(batchSize);
//			int loopIndex = 0;
//			int totalSize = 0;
//			long totalTime = 0;
//			boolean done = false;
//			while (!done) {
//				log.info(String.format("开始第%s次导出数据", ++loopIndex));
//				long startTime = System.currentTimeMillis();
//				solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);
//				QueryResponse rsp = solrClient.query(solrQuery);
//				String nextCursorMark = rsp.getNextCursorMark();
//				SolrDocumentList results = rsp.getResults();
//				List<String> jsons = new ArrayList<>(results.size());
//				for (SolrDocument doc : results) {
//					String json = JsonUtils.toJsonString(doc);
//					System.out.println(json);
////					if (null == file || file.length() >= fileMaxSize) {
////						file = new File(dataDir, exportTable + "-" + (fileIndex++));
////						fileSize = 0;
////					}
////					fileSize = fileSize + 1;
////					FileUtils.writeStringToFile(file,json, "utf-8", true);
//				}
//				totalSize += jsons.size();
//				long endTime = System.currentTimeMillis();
//				long time = endTime - startTime;
//				totalTime += time;
//				log.info("导出数据时间:" + time);
//				log.info("导出数据总时间:" + totalTime);
//				log.info("导出数据数量:" + jsons.size());
//				log.info("导出数据总数量:" + totalSize);
//				if (nextCursorMark == null || cursorMark.equals(nextCursorMark)) {
//					done = true;
//				}
//				cursorMark = nextCursorMark;
//			}
//		} catch (Exception exception) {
//			log.error(String.format("export data error from table[%s]", exportTable), exception);
//		} finally {
//			applicationContext.stop();
//		}
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		
//	}
//}
