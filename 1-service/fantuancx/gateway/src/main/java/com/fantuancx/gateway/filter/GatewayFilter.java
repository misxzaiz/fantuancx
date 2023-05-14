package com.fantuancx.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
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
        String uri = request.getURI().getPath();
        if(uri.equals("/login")){
            return chain.filter(exchange);
        }else{
            // 通过 gateway 获取请求头的 token
            HttpHeaders httpHeaders = request.getHeaders();
            String token = httpHeaders.getFirst("Authorization");
            log.info("token:{}",token);
            // 验证 token
            if(token != null && !token.equals("null")){
                JWTVerifier build = JWT.require(Algorithm.HMAC256("fantuan")).build();
                // 解码后 token 的内容
                log.info(String.valueOf(build.verify(token).getClaims()));
                return chain.filter(exchange);
            }
        }
        // 拦截
        log.info("登录失败！");
        // 设置状态码（HttpStatus.UNAUTHORIZED：未登录）
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 拦截请求
        return exchange.getResponse().setComplete();
    }
}
