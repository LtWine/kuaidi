package com.liumj.kuaidi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KuaidiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuaidiApplication.class, args);
	}

}
