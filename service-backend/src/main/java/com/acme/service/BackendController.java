package com.acme.service;

import io.opentelemetry.api.trace.Span;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Random;

@RestController
public class BackendController {

  private final RestTemplate restTemplate;

  private Random random = new Random();

  public BackendController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/service-a")
  public Response serviceA(HttpServletRequest request) {
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/service-b")
  public Response serviceB(HttpServletRequest request) {
    Thread.sleep(random.nextInt(10) * 1000);
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/service-c")
  public Response serviceC(HttpServletRequest request) {
    restTemplate.getForEntity("http://localhost:8380/db-1", Response.class).getBody();
    for (int i = 0; i < random.nextInt(5); i++) {
      restTemplate.getForEntity("http://localhost:8380/db-2", Response.class).getBody();
      Thread.sleep(1 * 1000);
    }

    return Response.ok();
  }

  @GetMapping("/service-d")
  public Response serviceD(HttpServletRequest request) {
    restTemplate.getForEntity("http://localhost:8380/db-3", Response.class).getBody();
    restTemplate.getForEntity("http://localhost:8380/db-4", Response.class).getBody();
    return Response.ok();
  }

}
