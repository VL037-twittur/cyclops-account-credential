package vincentlow.twittur.account.credential.client.service;

import vincentlow.twittur.account.credential.client.model.response.AccountProfileResponse;
import vincentlow.twittur.account.credential.web.model.request.CreateAccountRequest;

public interface AccountProfileFeignClientService {

  AccountProfileResponse createAccountProfile(CreateAccountRequest request, String accountCredentialId);
}
