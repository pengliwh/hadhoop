package com.chinacscs.platform.search.component;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import com.chinacscs.platform.commons.exception.IllegalArgumentAppException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: liusong
 * @date: 2019年1月18日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@Slf4j
public class RestHighLevelClientFactory {

	private final static String GET_NODES_PATH = "/_cat/nodes?v&h=http_address";
	private final static Request GET_NODES_REQUEST = new Request("get", GET_NODES_PATH);

	public static RestHighLevelClient build(HttpHost... httpHosts) {
		if (null == httpHosts || httpHosts.length == 0) {
			throw new IllegalArgumentAppException();
		}
		HttpHost[] getHttpHosts = null;
		for (HttpHost httpHost : httpHosts) {
			RestClient restClient = null;
			try {
				restClient = RestClient.builder(httpHost).build();
				Response response = restClient.performRequest(GET_NODES_REQUEST);
				String nodesInfoLines = EntityUtils.toString(response.getEntity());
				getHttpHosts = parserHttpHost(nodesInfoLines);
				if (null != getHttpHosts && getHttpHosts.length > 0) {
					break;
				}
			} catch (Exception exception) {
				log.warn(String.format("get node list failed from target[%s]",
						httpHost.toHostString()), exception);
			} finally {
				if (null != restClient) {
					try {
						restClient.close();
					} catch (IOException exception) {
						log.warn("close restclient error", exception);
					}
				}
			}
		}
		RestClientBuilder restClientBuilder = RestClient
				.builder(null != getHttpHosts ? getHttpHosts : httpHosts);
		return new RestHighLevelClient(restClientBuilder);
	}

	private static HttpHost[] parserHttpHost(String nodesInfoLines) {
		HttpHost[] result =null;
		if(null!=nodesInfoLines&&nodesInfoLines.length()>0) {
			String[] array = nodesInfoLines.split("\n");
			result = new HttpHost[array.length-1];
			for (int i=1;i<array.length;i++) {
				String line= array[i];
				String[] temp = line.split(":");
				HttpHost httpHost = new HttpHost(temp[0], Integer.valueOf(temp[1]), "http");
				result[i-1] = httpHost;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		RestHighLevelClientFactory.build(new HttpHost("10.100.44.75", 9200, "http"),
				new HttpHost("10.100.44.71", 9200, "http"));
	}
}
