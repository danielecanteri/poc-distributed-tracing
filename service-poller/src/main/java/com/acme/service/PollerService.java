package com.acme.service;

import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PollerService {

  @Autowired
  private RestTemplate restTemplate;

  @Scheduled(initialDelay = 2000, fixedRate = 20000)
  @WithSpan
  public void scheduled1() {
    restTemplate.getForEntity("http://localhost:8280/service-a", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 30000)
  @WithSpan
  public void scheduled2() {
    restTemplate.getForEntity("http://localhost:8280/service-b", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 50000)
  @WithSpan
  public void scheduled3() {
    restTemplate.getForEntity("http://localhost:8280/service-c", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 70000)
  @WithSpan
  public void scheduled4() {
    restTemplate.getForEntity("http://localhost:8280/service-d", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 80000)
  @WithSpan
  public void scheduled6() {
    restTemplate.getForEntity("http://localhost:8380/db-4", Map.class);
  }

}
