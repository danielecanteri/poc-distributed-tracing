package com.acme.service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Random;

@RestController
@Log4j2
public class DbController {

  private final DataSource dataSource;

  private Random random = new Random();

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

  @SneakyThrows
  @GetMapping("/db-1")
  public Response db1(HttpServletRequest request) {
    log.info("going to sleep");
    Thread.sleep(2000);
    log.info("awake");
    log.info("executing query from table 1");
    new JdbcTemplate(dataSource).queryForList("select * from table1");
    if (random.nextInt(20) == 0) {
      throw new NullPointerException();
    }
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/db-2")
  public Response db2(HttpServletRequest request) {
    log.info("going to sleep");
    Thread.sleep(4000);
    log.info("awake");
    log.info("executing query from table 2");
    new JdbcTemplate(dataSource).queryForList("select * from table2");
    if (random.nextInt(20) == 0) {
      throw new IllegalArgumentException();
    }
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/db-3")
  public Response db3(HttpServletRequest request) {
    log.info("going to sleep");
    Thread.sleep(6000);
    log.info("awake");
    log.info("executing query from table 3");
    new JdbcTemplate(dataSource).queryForList("select * from table3");
    if (random.nextInt(20) == 0) {
      throw new NumberFormatException();
    }
    return Response.ok();
  }

  @SneakyThrows
  @GetMapping("/db-4")
  public Response db4(HttpServletRequest request) {
    log.info("going to sleep");
    Thread.sleep(8000);
    log.info("awake");
    log.info("executing query from table 4");
    new JdbcTemplate(dataSource).queryForList("select * from table4");
    if (random.nextInt(20) == 0) {
      throw new ClassCastException();
    }
    return Response.ok();
  }


}
