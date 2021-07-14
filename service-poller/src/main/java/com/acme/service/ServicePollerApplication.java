package com.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class ServicePollerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServicePollerApplication.class, args);
  }

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
//		RestTemplateInterceptor restTemplateInterceptor = new RestTemplateInterceptor(openTelemetry);
//		restTemplate.getInterceptors().add(restTemplateInterceptor);
//		restTemplate.getInterceptors().add(new RestTemplateInterceptor);

		return restTemplate;
	}

}
