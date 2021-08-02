package com.acme.service;

import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;
import io.opentelemetry.extension.annotations.WithSpan;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Log4j2
public class PollerService {

  @Autowired
  private RestTemplate restTemplate;

  @Scheduled(initialDelay = 2000, fixedRate = 20000)
  @WithSpan
  public void scheduled1() {
    log.info("scheduled1");
    log.info("going to call service-a");
    restTemplate.getForEntity("http://localhost:8280/service-a", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 30000)
  @WithSpan
  public void scheduled2() {
    log.info("scheduled2");
    log.info("going to call service-b");
    restTemplate.getForEntity("http://localhost:8280/service-b", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 50000)
  @WithSpan
  public void scheduled3() {
    log.info("scheduled3");
    log.info("going to call service-c");
    restTemplate.getForEntity("http://localhost:8280/service-c", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 70000)
  @WithSpan
  public void scheduled4() {
    log.info("scheduled4");
    log.info("going to call service-d");
    restTemplate.getForEntity("http://localhost:8280/service-d", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 80000)
  @WithSpan
  public void scheduled6() {
    log.info("scheduled6");
    log.info("going to call db-4");
    restTemplate.getForEntity("http://localhost:8380/db-4", Map.class);
  }

}
