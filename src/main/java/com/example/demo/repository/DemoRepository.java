package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Mapper
public interface DemoRepository { // RetryBase継承すると起動時にエラーになる

  @Select("select title from todo order by id fetch first 1 rows only;")
  String select();

}
