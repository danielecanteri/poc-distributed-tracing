package com.acme.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DbController {

  @GetMapping("/db-1")
  public Response serviceA(HttpServletRequest request) {
    return Response.ok();
  }


}
