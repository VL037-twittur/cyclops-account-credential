package vincentlow.twittur.account.credential.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"vincentlow.twittur.account.credential.*"})
@EntityScan(basePackages = {"vincentlow.twittur.account.credential.*"})
@EnableJpaRepositories(basePackages = {"vincentlow.twittur.account.credential.*"})
@EnableFeignClients(basePackages = {"vincentlow.twittur.account.credential.*"})
// if you don't use "basePackages", might not read the @FeignClient or other beans
public class CyclopsAccountCredentialApplication {

  public static void main(String[] args) {

    SpringApplication.run(CyclopsAccountCredentialApplication.class);
  }
}
