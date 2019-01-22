package com.chinacscs.platform.search.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinacscs.platform.search.component.RestHighLevelClientFactory;

/**
 * @author:  liusong
 * @date:    2019年1月12日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
@Configuration
public class AppConfig {

	static String proxyHost = "10.100.44.72";
	static int proxyPort = 8080;
	
	@Value("${proxy.enable}")
	private boolean proxyEnable;
	
	@Value("${es.host}")
	private String esHost;

	@Value("${es.port}")
	private int esPort;
	
	@Bean(destroyMethod="close")
	public RestHighLevelClient initRestTemplate() {
		return RestHighLevelClientFactory
				.build(new HttpHost(esHost, esPort, "http"));
	}
	
	public BulkProcessor initBulkProcessor() {
		return null;
	}
	
	@Bean
	public HttpClient initHttpClient() {
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new MyConnectionSocketFactory(proxyEnable)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
				reg);
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
				.build();
		return httpclient;
	}


	class MyConnectionSocketFactory implements ConnectionSocketFactory {

		boolean proxyEnable;

		MyConnectionSocketFactory(boolean proxyEnable) {
			this.proxyEnable = proxyEnable;
		}
		@Override
		public Socket createSocket(final HttpContext context) throws IOException {
			Socket socket = null;
			if (proxyEnable) {
				InetSocketAddress socksaddr = new InetSocketAddress(proxyHost, proxyPort);
				Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
				socket = new Socket(proxy);
			} else {
				socket = new Socket();
			}
			return socket;
		}

		@Override
		public Socket connectSocket(final int connectTimeout, final Socket socket,
				final HttpHost host, final InetSocketAddress remoteAddress,
				final InetSocketAddress localAddress, final HttpContext context)
				throws IOException, ConnectTimeoutException {
			Socket sock;
			if (socket != null) {
				sock = socket;
			} else {
				sock = createSocket(context);
			}
			if (localAddress != null) {
				sock.bind(localAddress);
			}
			try {
				sock.connect(remoteAddress, connectTimeout);
			} catch (SocketTimeoutException ex) {
				throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
			}
			return sock;
		}
	}
}
