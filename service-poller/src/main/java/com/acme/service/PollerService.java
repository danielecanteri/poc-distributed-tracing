package com.acme.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PollerService {

  private final RestTemplate restTemplate;

  public PollerService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Scheduled(initialDelay = 2000, fixedRate = 2000)
  public void scheduled1() {
    restTemplate.getForEntity("http://localhost:8280/service-a", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 3000)
  public void scheduled2() {
    restTemplate.getForEntity("http://localhost:8280/service-b", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 5000)
  public void scheduled3() {
    restTemplate.getForEntity("http://localhost:8280/service-c", Map.class);
  }

  @Scheduled(initialDelay = 2000, fixedRate = 7000)
  public void scheduled4() {
    restTemplate.getForEntity("http://localhost:8280/service-d", Map.class);
  }
}
