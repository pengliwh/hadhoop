//package com.chinacscs.platform.gateway.filter;
//
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.chinacscs.platform.commons.util.JsonUtils;
//import com.chinacscs.usercenter.dto.Resource;
//import com.chinacscs.usercenter.dto.UserRole;
//import com.chinacscs.usercenter.service.ResourceService;
//import com.chinacscs.usercenter.service.RoleResourceService;
//import com.google.common.collect.Multimaps;
//
//import reactor.core.publisher.Mono;
//
////@Component
//public class AuthorityGlobalFilter implements GlobalFilter, Ordered {
//
//	private static final Logger logger = LoggerFactory.getLogger(AuthorityGlobalFilter.class);
//
//	private final static String TOKEN_COOKIE = "token";
//
//	private final static String AUTHORIZATION = "authorization";
//
//	private Map<String, Resource> resourceMap;
//
//	private Map<String, String> resourceRoleMap;
//
//	@Autowired
//	private ResourceService resourceService;
//
//	@Autowired
//	private RoleResourceService roleResourceService;
//
//	@PostConstruct
//	public void init() {
//		resourceMap = new HashMap<>();
//		// Multimaps.newMultimap(map, factory)
//		List<Resource> list = resourceService.listAll();
//		for (Resource resource : list) {
//			resourceMap.put(resource.getPath(), resource);
//		}
//	}
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		ServerHttpRequest reuqest = exchange.getRequest();
//		ServerHttpResponse response = exchange.getResponse();
//		String path = reuqest.getURI().getPath();
//		Resource resource = resourceMap.get(path);
//		if (null == resource) {
//			response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
//			return response.setComplete();
//		}
//		if (!resource.isOpen()) {
//			HttpCookie tokenCookie = reuqest.getCookies().getFirst(TOKEN_COOKIE);
//			String token = null;
//			if (null == tokenCookie) {
//				token = reuqest.getHeaders().getFirst(AUTHORIZATION);
//			} else {
//				token = tokenCookie.getValue();
//			}
//			if (null == token) {
//				response.setStatusCode(HttpStatus.UNAUTHORIZED);
//				return response.setComplete();
//			}
//			UserRole userRole = null;
//			try {
//				byte[] userJsonBytes = Base64.getDecoder().decode(token);
//				String userJson = new String(userJsonBytes);
//				userRole = JsonUtils.toObject(userJson, UserRole.class);
//			} catch (Exception exception) {
//				logger.error(String.format("Parse token error and the request[%s] is illegal", reuqest.toString()),
//						exception);
//				response.setStatusCode(HttpStatus.BAD_REQUEST);
//				return response.setComplete();
//			}
//			if (!validation(userRole.getId(), path)) {
//				response.setStatusCode(HttpStatus.BAD_REQUEST);
//				return response.setComplete();
//			}
//		}
//		return chain.filter(exchange);
//	}
//
//	private boolean validation(Long roleId, String path) {
//		return false;
//	}
//
//	@Override
//	public int getOrder() {
//		return 0;
//	}
//}
