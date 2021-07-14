package com.acme.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class BackendController {

  private final RestTemplate restTemplate;

  public BackendController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/service-a")
  public Response serviceA(HttpServletRequest request) {
      System.out.println(request);
      System.out.println(request.getHeaderNames());
      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String s = headerNames.nextElement();
        System.out.println(s);
        System.out.println(request.getHeader(s));
        System.out.println("---");
      }

    return Response.ok();
  }

  @GetMapping("/service-b")
  public Response serviceB(HttpServletRequest request) {
    return Response.ok();
  }

  @GetMapping("/service-c")
  public Response serviceC(HttpServletRequest request) {
    restTemplate.getForEntity("http://localhost:8380/db-1", Response.class).getBody();
    return Response.ok();
  }

  @GetMapping("/service-d")
  public Response serviceD(HttpServletRequest request) {
    return Response.ok();
  }

}
