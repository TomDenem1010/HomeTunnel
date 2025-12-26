package com.home.tunnel.client;

import com.home.tunnel.properties.VideoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(VideoProperties.class)
public class VideoConfig {

  @Bean
  public VideoClient videoClient(
      final RestClient.Builder builder, final VideoProperties videoProperties) {
    var restClient = builder.baseUrl(videoProperties.getUrl()).build();
    var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return factory.createClient(VideoClient.class);
  }
}
