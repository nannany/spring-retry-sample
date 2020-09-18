package com.example.demo.controller;

import com.example.demo.model.Demo;
import com.example.demo.repository.ExecQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

  private ExecQuery execQuery;

  public DemoController(ExecQuery execQuery) {
    this.execQuery = execQuery;
  }

  @GetMapping("/demo")
  public Demo getDemo() {

    String str = execQuery.useDemoRepo();

    return new Demo(true, str);
  }

}
