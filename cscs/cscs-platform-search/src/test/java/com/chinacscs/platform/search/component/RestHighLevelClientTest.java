package com.chinacscs.platform.search.component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import com.chinacscs.platform.search.BaseTest;

/**
 * @author: liusong
 * @date: 2019年1月17日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class RestHighLevelClientTest extends BaseTest {

	@Value("${es.host}")
	private String esHost;

	@Value("${es.port}")
	private int esPort;

	RestHighLevelClient restClient;


	@Before
	public void init() throws Exception {
		restClient = new RestHighLevelClient(RestClient.builder(
				new HttpHost(esHost, esPort, "http"), new HttpHost("10.100.44.72", esPort, "http"),
				new HttpHost("10.100.44.75", esPort, "http")));
	}

	@Test
	public void testSearch() throws Exception {
		File searchTemplateFile = ResourceUtils
				.getFile("classpath:search_templtae/company_search.json");
		String searchTemplateScript = FileUtils.readFileToString(searchTemplateFile, "utf-8");
		int size = 10;
		for (int i = 0; i < size; i++) {
			Map<String, Object> scriptParams = new HashMap<>();
			scriptParams.put("industry", "industry");
			// scriptParams.put("name", "name");
			SearchTemplateRequest request = new SearchTemplateRequest();
			SearchRequest searchRequest = new SearchRequest("company");
			searchRequest.types("company");
			request.setRequest(searchRequest);
			request.setScriptType(ScriptType.INLINE);
			request.setScript(searchTemplateScript);
			request.setScriptParams(scriptParams);
			SearchTemplateResponse response = restClient.searchTemplate(request,
					RequestOptions.DEFAULT);
			SearchHits searchHits = response.getResponse().getHits();
			for (SearchHit searchHit : searchHits.getHits()) {
				System.out.println(searchHit.toString());
				System.out.println("--------------------");
			}
		}
	}

	@After
	public void close() throws IOException {
		restClient.close();
	}
}
