package vincentlow.twittur.account.credential.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vincentlow.twittur.account.credential.client.model.request.CreateAccountProfileRequest;
import vincentlow.twittur.account.credential.client.model.response.AccountProfileResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;

@FeignClient(name = "gargoyle-account-profile")
public interface AccountProfileFeignClient {

  @PostMapping("api/v1/accounts")
  ResponseEntity<ApiSingleResponse<AccountProfileResponse>> createAccountProfile(
      @RequestBody CreateAccountProfileRequest request);

}
