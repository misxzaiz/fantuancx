package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class TokenFilter implements Filter {

    // 过滤器或拦截器在生效时，比你的注入要更快，导致redisTemplate还没注入就使用所以会报空指针异常。
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求头中的token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String token = (String) RedisUtils.get("token");
        // 如果token正确或者请求的是 /login，则继续执行后续操作
        if (uri.endsWith("/login") || method.equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (authorization!= null && authorization.equals(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 如果token不正确则返回401错误码
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 在响应头中添加 Access-Control-Allow-Origin 字段，值为 '*' 表示接受任意来源的跨域请求。
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
    }

    @Override
    public void destroy() {
        // 销毁操作
    }
}
