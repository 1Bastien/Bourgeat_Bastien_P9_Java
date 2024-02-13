package com.medilabo.EurekaSrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaSrvApplication.class, args);
	}

}
