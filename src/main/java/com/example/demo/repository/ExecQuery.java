package com.example.demo.repository;

import org.springframework.dao.QueryTimeoutException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Retryable(value = {QueryTimeoutException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500))
public class ExecQuery {

  private final DemoRepository demoRepository;

  public ExecQuery(DemoRepository demoRepository) {

    this.demoRepository = demoRepository;
  }

  public String useDemoRepo() {
    return demoRepository.select();
  }

  @Recover
  String recover() {
    return "recover";
  }

}
