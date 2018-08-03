package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DemoApplication {
    Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Bean
    WebClient getWebClient(){
	    return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client){
	    return args -> {
	        client.get()
                .uri("/data")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Long.class)
                .map(s ->String.valueOf(s))
                .subscribe(msg -> logger.info(msg));
        };
    }

	public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class);
	}
}
