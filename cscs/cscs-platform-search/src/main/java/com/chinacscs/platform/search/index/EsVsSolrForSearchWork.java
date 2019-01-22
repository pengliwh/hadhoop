/*
 * package com.chinacscs.platform.search.index;
 * 
 * import java.io.IOException; import java.util.LinkedHashMap; import
 * java.util.List; import java.util.Map; import
 * java.util.concurrent.atomic.AtomicLong;
 * 
 * import org.apache.commons.io.FileUtils; import
 * org.apache.http.client.HttpClient; import
 * org.apache.solr.client.solrj.SolrQuery; import
 * org.apache.solr.client.solrj.impl.HttpSolrClient; import
 * org.apache.solr.client.solrj.impl.HttpSolrClient.Builder; import
 * org.apache.solr.client.solrj.response.QueryResponse; import
 * org.springframework.beans.factory.InitializingBean; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.core.io.ClassPathResource; import
 * org.springframework.core.io.Resource; import
 * org.springframework.stereotype.Component;
 * 
 * import com.chinacscs.platform.search.component.RestOperationForEs;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 *//**
	 * @author: liusong
	 * @date: 2019年1月15日
	 * @email: 359852326@qq.com
	 * @version:
	 * @describe: //TODO
	 *//*
		 * @Slf4j
		 * 
		 * @Component("EsVsSolrForSearchWork") public class
		 * EsVsSolrForSearchWork implements StressTestWorker, InitializingBean {
		 * 
		 * private final static String queryExpTemplate =
		 * "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"company_nm\",\"query\":\"%s\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"aggs\":{}}";
		 * 
		 * @Value("${app.home}") private String appHome;
		 * 
		 * @Value("${export.table}") private String exportTable;
		 * 
		 * @Value("${es.url}") private String esUrl;
		 * 
		 * @Value("${sorl.url}") private String sorlUrl;
		 * 
		 * @Value("${proxy.enable}") private boolean proxyEnable;
		 * 
		 * @Autowired private HttpClient httpClient;
		 * 
		 * private HttpSolrClient solrClient;
		 * 
		 * @Autowired private RestOperationForEs restOperationForEs;
		 * 
		 * @Override public void afterPropertiesSet() throws Exception { String
		 * url = sorlUrl + "/" + exportTable; HttpSolrClient.Builder solrBuilder
		 * = new Builder(); solrBuilder.withBaseSolrUrl(url);
		 * solrBuilder.withHttpClient(httpClient); solrClient =
		 * solrBuilder.build(); }
		 * 
		 * protected void searchForSolr(String key) { SolrQuery query = new
		 * SolrQuery(); query.set("q", key); query.set("bf",
		 * "log(max(regcapital,1))^8"); query.set("lowercaseOperators", "true");
		 * query.set("fl", "score,*"); query.set("defType", "edismax");
		 * query.set("qf", "lvl1_keyword^100 lvl2_keyword lvl3_keyword^0.1");
		 * query.set("pf", "lvl2_keyword"); query.set("stopwords", "true");
		 * query.set("shards.tolerant", "true"); query.addSort("score",
		 * SolrQuery.ORDER.desc); query.addSort("regcapital",
		 * SolrQuery.ORDER.desc); query.setStart(0); query.setRows(10); try {
		 * QueryResponse rsp = solrClient.query(query); rsp.getResults(); }
		 * catch (Exception exception) { throw new RuntimeException(exception);
		 * } }
		 * 
		 * protected void searchForEs(String key) { String queryExp =
		 * String.format(queryExpTemplate, key); Map<String, Object> result =
		 * restOperationForEs.search(esUrl, queryExp); result.size(); }
		 * 
		 * private List<String> loadCompanyNames() { Resource fileRource = new
		 * ClassPathResource("es_vs_solr_company_list.txt"); try { return
		 * FileUtils.readLines(fileRource.getFile(), "utf-8"); } catch
		 * (IOException exception) { throw new RuntimeException(exception); } }
		 * 
		 * @Override public void run(String job, String group) { List<String>
		 * companyNames = loadCompanyNames(); Map<String, AtomicLong> solrMap =
		 * new LinkedHashMap<>(); Map<String, AtomicLong> esMap = new
		 * LinkedHashMap<>(); int outLoopCount = 3;
		 * log.info("\t\t solr \t\tes \t\tcompany"); for (int i = 0; i <
		 * outLoopCount; i++) { for (String companyName : companyNames) {
		 * 
		 * long solrTime = 0l; long esTime = 0l; try { long solrStartTime =
		 * System.currentTimeMillis(); searchForSolr(companyName); long
		 * solrEndTime = System.currentTimeMillis(); solrTime = solrEndTime -
		 * solrStartTime;
		 * 
		 * long esStartTime = System.currentTimeMillis();
		 * searchForEs(companyName); long esEndTime =
		 * System.currentTimeMillis(); esTime = esEndTime - esStartTime;
		 * 
		 * solrMap.computeIfAbsent(companyName, newKey -> new AtomicLong(0))
		 * .addAndGet(solrTime);
		 * 
		 * esMap.computeIfAbsent(companyName, newKey -> new AtomicLong(0))
		 * .addAndGet(esTime); log.info("\t\t "+solrTime + "\t\t" + esTime +
		 * "\t\t" + companyName); } catch (Exception e) {
		 * 
		 * } } } log.info(
		 * "---------------------------statistics---------------------------");
		 * long totalSolrTime=0l; long totalEsTime=0l; for (String companyName :
		 * companyNames) { long solrTime = solrMap.get(companyName).longValue();
		 * solrTime = solrTime / outLoopCount; totalSolrTime+=solrTime;
		 * 
		 * long esTime = esMap.get(companyName).longValue(); esTime = esTime /
		 * outLoopCount; totalEsTime+=esTime; log.info("\t\t "+solrTime + "\t\t"
		 * + esTime + "\t\t" + companyName); } long
		 * avgSolrTime=totalSolrTime/companyNames.size(); long
		 * avgEsTime=totalSolrTime/companyNames.size();
		 * log.info("\t\t "+avgSolrTime + "\t\t" + avgEsTime + "\t\t avg");
		 * log.info("\t\t "+totalSolrTime + "\t\t" + totalEsTime +
		 * "\t\t total"); } }
		 */