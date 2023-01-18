package com.liumj.kuaidi.config;

import java.util.Optional;
import java.util.function.Function;

import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import static com.liumj.kuaidi.config.Constant.SEMICOLON;


@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Value("${swagger.enable:true}")
	private boolean enableSwagger;

	@Bean
	public Docket createAgentApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("服务接口")
				.genericModelSubstitutes(DeferredResult.class)
				.apiInfo(agentApiInfo())
				.select()
				.apis(basePackage())
				.paths(PathSelectors.any())
				.build()
				.enable(enableSwagger);
	}

	private ApiInfo agentApiInfo() {
		return new ApiInfoBuilder()
				.title("服务")
				.description("api")
				.version("1.0")
				.build();
	}

	/**
	 * 重写basePackage方法，使能够实现多包访问
	 */
	@SuppressWarnings("java:S1874")
	private static Predicate<RequestHandler> basePackage() {
		return input -> Optional.ofNullable(input.declaringClass())
				.map(handlerPackage())
				.orElse(true);
	}

	private static Function<Class<?>, Boolean> handlerPackage() {
		return input -> {
			// 循环判断匹配
			for (String strPackage : "com.liumj.kuaidi.controller".split(SEMICOLON)) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
//			for (String strPackage : "".split(SEMICOLON)) {
//				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
//				if (isMatch) {
//					return true;
//				}
//			}
			return false;
		};
	}
}
