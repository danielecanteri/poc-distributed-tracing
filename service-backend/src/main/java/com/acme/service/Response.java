package com.acme.service;

import lombok.Data;

@Data
public class Response {
  private String message;

  public static Response ok() {
    Response response = new Response();
    response.message = "OK";
    return response;
  }
}
