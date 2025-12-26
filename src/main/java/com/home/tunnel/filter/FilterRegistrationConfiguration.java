package com.home.tunnel.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class FilterRegistrationConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public FilterRegistrationBean<CommunicationRequestResponseLoggingFilter>
      communicationRequestResponseLoggingFilterRegistration(
          final CommunicationRequestResponseLoggingFilter
              communicationRequestResponseLoggingFilter) {
    FilterRegistrationBean<CommunicationRequestResponseLoggingFilter> registrationBean =
        new FilterRegistrationBean<>();

    registrationBean.setFilter(communicationRequestResponseLoggingFilter);
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(1);

    return registrationBean;
  }
}
