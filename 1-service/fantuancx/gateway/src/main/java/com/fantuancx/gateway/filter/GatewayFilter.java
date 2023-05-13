package com.fantuancx.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@Component
public class GatewayFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 通过 request 获取请求参数
        // ServerRequest
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        // 2. 获取参数中的 fantuan 的值
        String username = queryParams.getFirst("fantuan");
        // 3. 判断 username 的值是否等于"fantuan"
        if (username!=null && username.equals("fantuan")){
            log.info("登录成功！");
            // 3.1 放行
            return chain.filter(exchange);
        }
        // 4. 拦截
        log.info("登录失败！");
        // 4.1 设置状态码（HttpStatus.UNAUTHORIZED：未登录）
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 4.2 拦截请求
        return exchange.getResponse().setComplete();
    }
}
