package com.acme.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@RestController
public class DbController {

  private final DataSource dataSource;

  public DbController(DataSource dataSource) {
    this.dataSource = dataSource;

    new JdbcTemplate(dataSource).execute("CREATE TABLE table1 ( \n" +
        "   id INT NOT NULL \n" +
        ");");
    new JdbcTemplate(dataSource).execute("CREATE TABLE table2 ( \n" +
        "   id INT NOT NULL \n" +
        ");");
    new JdbcTemplate(dataSource).execute("CREATE TABLE table3 ( \n" +
        "   id INT NOT NULL \n" +
        ");");
    new JdbcTemplate(dataSource).execute("CREATE TABLE table4 ( \n" +
        "   id INT NOT NULL \n" +
        ");");
  }

  @GetMapping("/db-1")
  public Response db1(HttpServletRequest request) {
    new JdbcTemplate(dataSource).queryForList("select * from table1");
    return Response.ok();
  }

  @GetMapping("/db-2")
  public Response db2(HttpServletRequest request) {
    new JdbcTemplate(dataSource).queryForList("select * from table2");
    return Response.ok();
  }

  @GetMapping("/db-3")
  public Response db3(HttpServletRequest request) {
    new JdbcTemplate(dataSource).queryForList("select * from table3");
    return Response.ok();
  }

  @GetMapping("/db-4")
  public Response db4(HttpServletRequest request) {
    new JdbcTemplate(dataSource).queryForList("select * from table4");
    return Response.ok();
  }


}
