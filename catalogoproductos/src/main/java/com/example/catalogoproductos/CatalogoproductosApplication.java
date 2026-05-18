package com.example.catalogoproductos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CatalogoproductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoproductosApplication.class, args);
	}

}
