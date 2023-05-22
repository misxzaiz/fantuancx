package com.fantuancx.gateway.filter;

import com.fantuancx.gateway.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Order(-1)
@Component
public class GatewayFilter implements GlobalFilter {

    // 放行路由
    private final String[] skipAuthUrls = new String[]{
            "/login",
            "/user/register",
            "/content/**",
            "/**/v2/api-docs",
            "/**/swagger-ui.html",
            "/**/swagger-resources/**",
            "/hotel/**" // ES 测试
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        // 跳过不需要验证的路径
        if (isSkipUrl(url)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        // 从请求头中取得token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        // token是否为空
        if (StringUtils.isEmpty(token)) {
            return fail(response,"token为空，鉴权失败");
        }
        // 请求中的token是否有效
        String jwt = JWTUtil.getJWT(token).toString();
        log.info("jwt:{}",jwt);
        if(StringUtils.isEmpty(jwt)){
            return fail(response,"token不合法");
        }
        // 校验token是否过期
        if(!JWTUtil.validateSignature(token)){
            return fail(response,"token已过期");
        }
        //如果各种判断都通过，执行chain上的其他业务逻辑
        return chain.filter(exchange);
    }


    // 响应错误信息
    private Mono<Void> fail(ServerHttpResponse response, String message){
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

    /**
     * 判断当前访问的url是否开头URI是在配置的忽略url列表中
     *
     * @param url
     * @return
     */
    public boolean isSkipUrl(String url) {
        if(StringUtils.isEmpty(url)){
            return false;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for (String skipAuthUrl : skipAuthUrls) {
            if(matcher.match(skipAuthUrl, url)){
                return true;
            }
        }
        return false;
    }
}
