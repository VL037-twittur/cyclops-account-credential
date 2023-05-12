package vincentlow.twittur.account.credential.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {

    return new RestTemplate();
  }
}
