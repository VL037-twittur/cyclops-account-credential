//package vincentlow.twittur.account.credential.client.service;
//
//import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//import vincentlow.twittur.account.credential.client.AccountProfileFeignClient;
//import vincentlow.twittur.account.credential.web.model.request.CreateAccountRequest;
//import vincentlow.twittur.account.profile.web.model.response.AccountProfileResponse;
//import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
//
//@Slf4j
//@Service
//public class AccountProfileFeignClientServiceImpl implements AccountProfileFeignClientService {
//
//  @Autowired
//  private AccountProfileFeignClient accountProfileFeignClient;
//
//  @Override
//  public AccountProfileResponse createAccountProfile(CreateAccountRequest request, String accountCredentialId) {
//
//    try {
//      ResponseEntity<ApiSingleResponse<AccountProfileResponse>> response =
//          accountProfileFeignClient.createAccountProfile(request, accountCredentialId);
//      String error = response.getBody()
//          .getError();
//
//      if (Objects.nonNull(error)) {
//        log.warn(
//            "#AccountProfileFeignClientServiceImpl#createAccountProfile FAILED! with request: {}, accountCredentialId: {}, and error: {}",
//            request, accountCredentialId, error);
//        return null;
//      }
//      return response.getBody()
//          .getData();
//    } catch (Exception e) {
//      log.error(
//          "#AccountProfileFeignClientServiceImpl#createAccountProfile ERROR! with request: {}, accountCredentialId: {}, and error: {}",
//          request, accountCredentialId, e.getMessage(), e);
//      return null;
//    }
//  }
//}
