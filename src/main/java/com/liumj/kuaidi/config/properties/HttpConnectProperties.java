package com.liumj.kuaidi.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * RestTemplate 配置
 *
 */
@Data
@Configuration
public class HttpConnectProperties {

    @Value("${dataview.http.timeout:5000}")
    private Integer timeOut;
    @Value("${dataview.http.poolSize:100}")
    private Integer poolSize;

}
