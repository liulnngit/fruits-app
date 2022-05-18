package com.example.emoji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmojiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmojiServiceApplication.class, args);
	}

}
