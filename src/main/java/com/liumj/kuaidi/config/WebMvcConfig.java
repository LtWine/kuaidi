package com.liumj.kuaidi.config;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.liumj.kuaidi.aop.LoginHandler;
import com.liumj.kuaidi.config.properties.HttpConnectProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lyk
 * @author ltwine
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final LoginHandler loginHandler;

	public WebMvcConfig(LoginHandler loginHandler) {
		super();
		this.loginHandler = loginHandler;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] swaggerPaths = new String[] {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/doc.html"};
		registry.addInterceptor(loginHandler).addPathPatterns("/**").excludePathPatterns(swaggerPaths);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastConverter.setFastJsonConfig(fastJsonConfig);
		fastConverter.setDefaultCharset(StandardCharsets.UTF_8);
		//将fastJson加在jackson前面
		converters.add(7, fastConverter);
	}

	@Bean
	public RestTemplate restTemplate(HttpConnectProperties httpConnectProperties) {
		ConnectionPool connectionPool = new ConnectionPool(httpConnectProperties.getPoolSize(), 10, TimeUnit.SECONDS);
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.retryOnConnectionFailure(false)
				.connectionPool(connectionPool)
				.connectTimeout(httpConnectProperties.getTimeOut(), TimeUnit.MILLISECONDS)
				.readTimeout(httpConnectProperties.getTimeOut(), TimeUnit.MILLISECONDS)
				.writeTimeout(httpConnectProperties.getTimeOut(), TimeUnit.MILLISECONDS)
				.build();

		return new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
	}

}
