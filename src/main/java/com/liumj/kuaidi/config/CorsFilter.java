package com.liumj.kuaidi.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

/**
 *
 */
@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 允许的来源
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 是否允许证书
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 允许的请求方式
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 预检请求的有效期
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        //放开同源限制
        response.setHeader("Timing-Allow-Origin","*");
        chain.doFilter(req, res);
    }
}

