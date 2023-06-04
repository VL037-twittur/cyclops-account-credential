package vincentlow.twittur.account.credential.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vincentlow.twittur.account.credential.client.model.request.EmailRequest;
import vincentlow.twittur.account.credential.client.model.response.EmailResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;

@FeignClient(name = "kraken-information")
public interface InformationFeignClient {

  @PostMapping("api/v1/emails")
  ResponseEntity<ApiSingleResponse<EmailResponse>> sendEmail(@RequestBody EmailRequest request);
}
