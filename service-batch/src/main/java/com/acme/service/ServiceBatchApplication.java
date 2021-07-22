package com.acme.service;

import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceBatchApplication {

  @WithSpan
  public static void main(String[] args) {
    SpringApplication.run(ServiceBatchApplication.class, args);
  }

}
