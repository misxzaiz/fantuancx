package com.fantuancx.gateway.filter;

import com.fantuancx.jwt.common.GetSetJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 拦截请求，检验是否已经登录
 */
@Slf4j
@Order(-1)
@Component
public class GatewayFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 通过 request 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        // 2. 放行登录请求
        if(request.getURI().getPath().equals("/login")){
            return chain.filter(exchange);
        }else{
            // 3. 通过 gateway 获取请求头的 token
            String token = request.getHeaders().getFirst("Authorization");
            // 4. 验证 token
            if(token != null && !token.equals("null")){
                if(GetSetJWT.validateSignature(token)){
                    return chain.filter(exchange);
                }
            }
        }
        // 5. 设置状态码（HttpStatus.UNAUTHORIZED：未登录）
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 6. 拦截请求
        return exchange.getResponse().setComplete();
    }
}
