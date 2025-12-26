package com.home.tunnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.home.common.commonication"})
@EntityScan(basePackages = {"com.home.common.commonication.dao"})
public class TunnelApplication {

  public static void main(String[] args) {
    SpringApplication.run(TunnelApplication.class, args);
  }
}
