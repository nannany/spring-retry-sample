package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.demo.model.Demo;
import com.example.demo.repository.DemoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    MockitoTestExecutionListener.class})
public class RetryTest {

  @MockBean(name = "demoRepository") // name指定しないとmockが動かない。
  private DemoRepository demoRepository;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void recoveryTest() {
    given(this.demoRepository.select()).willThrow(new QueryTimeoutException("qt"));

    final ResponseEntity<Demo> responseEntity =
        testRestTemplate.getForEntity("/demo", Demo.class);

    assertThat(responseEntity.getBody().getDbData()).isEqualTo("recover");

  }

  @Test
  void retryTest() {
    given(this.demoRepository.select()).willThrow(new QueryTimeoutException("qt"))
        .willReturn("title");

    final ResponseEntity<Demo> responseEntity =
        testRestTemplate.getForEntity("/demo", Demo.class);

    assertThat(responseEntity.getBody().getDbData()).isEqualTo("title");

  }
}
