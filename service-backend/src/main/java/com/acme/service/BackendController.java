package com.acme.service;

import io.opentelemetry.api.trace.Span;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Random;

@RestController
@Log4j2
public class BackendController {

  private final RestTemplate restTemplate;

  private Random random = new Random();

  public BackendController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/service-a")
  public Response serviceA(HttpServletRequest request) {
    log.info("service a");
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/service-b")
  public Response serviceB(HttpServletRequest request) {
    log.info("going to sleep");
    Thread.sleep(random.nextInt(10) * 1000);
    log.info("awake!");
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/service-c")
  public Response serviceC(HttpServletRequest request) {
    log.info("calling db 1");
    restTemplate.getForEntity("http://localhost:8380/db-1", Response.class).getBody();
    for (int i = 0; i < random.nextInt(5); i++) {
      log.info("calling db 2");
      restTemplate.getForEntity("http://localhost:8380/db-2", Response.class).getBody();
      log.info("going to sleep");
      Thread.sleep(1 * 1000);
      log.info("awake!");
    }

    return Response.ok();
  }

  @GetMapping("/service-d")
  public Response serviceD(HttpServletRequest request) {
    log.info("calling db 3");
    restTemplate.getForEntity("http://localhost:8380/db-3", Response.class).getBody();
    log.info("calling db 4");
    restTemplate.getForEntity("http://localhost:8380/db-4", Response.class).getBody();
    log.info("done");
    return Response.ok();
  }

}
