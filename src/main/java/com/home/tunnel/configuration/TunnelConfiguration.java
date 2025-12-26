package com.home.tunnel.configuration;

import com.home.common.commonication.mapper.CommunicationRequestContentMapper;
import com.home.common.commonication.mapper.CommunicationResponseContentMapper;
import com.home.common.commonication.mapper.CommunicationTransactionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TunnelConfiguration {

  @Bean
  public CommunicationTransactionMapper communicationTransactionMapper() {
    return new CommunicationTransactionMapper();
  }

  @Bean
  public CommunicationRequestContentMapper communicationRequestContentMapper() {
    return new CommunicationRequestContentMapper();
  }

  @Bean
  public CommunicationResponseContentMapper communicationResponseContentMapper() {
    return new CommunicationResponseContentMapper();
  }
}
