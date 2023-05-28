package vincentlow.twittur.account.credential.web.controller;

import static vincentlow.twittur.account.credential.util.ObjectMappingHelper.toResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.account.credential.model.constant.ApiPath;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.service.AccountCredentialService;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountEmailRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPasswordRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPhoneNumberRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountUsernameRequest;
import vincentlow.twittur.account.credential.web.model.response.AccountCredentialResponse;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.api.ApiResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;

@Slf4j
@RestController
@RequestMapping(ApiPath.ACCOUNT_CREDENTIAL)
public class AccountCredentialController extends BaseController {

  @Autowired
  private AccountCredentialService accountCredentialService;

  @GetMapping("/@{username}")
  public ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> getAccountCredential(
      @PathVariable String username) {

    try {
      AccountCredential account = accountCredentialService.getAccountCredentialByUsername(username);
      AccountCredentialResponse response = toResponse(account, AccountCredentialResponse.class);
      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#getAccountCredential ERROR! with username: {}, and error: {}", username,
          e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> getAccountCredentialById(
      @PathVariable String id) {

    try {
      AccountCredential account = accountCredentialService.getAccountCredentialById(id);
      AccountCredentialResponse response = toResponse(account, AccountCredentialResponse.class);

      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#getAccountCredentialById ERROR! with id: {}, and error: {}", id,
          e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PutMapping("/{id}/username")
  public ResponseEntity<ApiResponse> updateAccountUsername(
      @PathVariable("id") String id, @RequestBody UpdateAccountUsernameRequest request) {

    try {
      accountCredentialService.updateAccountUsernameById(id, request);
      return toSuccessResponseEntity(successResponse);
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#updateAccountUsername ERROR! with id: {}, request: {}, and error: {}", id,
          request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PutMapping("/{id}/emailAddress")
  public ResponseEntity<ApiResponse> updateAccountEmail(
      @PathVariable("id") String id, @RequestBody UpdateAccountEmailRequest request) {

    try {
      accountCredentialService.updateAccountEmailAddressById(id, request);
      return toSuccessResponseEntity(successResponse);
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#updateAccountEmail ERROR! with id: {}, request: {}, and error: {}", id,
          request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PutMapping("/{id}/phoneNumber")
  public ResponseEntity<ApiResponse> updateAccountPhoneNumber(@PathVariable("id") String id,
      @RequestBody UpdateAccountPhoneNumberRequest request) {

    try {
      accountCredentialService.updateAccountPhoneNumberById(id, request);
      return toSuccessResponseEntity(successResponse);
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#updateAccountPhoneNumber ERROR! with id: {}, request: {}, and error: {}",
          id, request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<ApiResponse> updateAccountPassword(@PathVariable("id") String id,
      @RequestBody UpdateAccountPasswordRequest request) {

    try {
      accountCredentialService.updateAccountPasswordById(id, request);
      return toSuccessResponseEntity(successResponse);
    } catch (RuntimeException e) {
      log.error("#AccountCredentialController#updateAccountPassword ERROR! with id: {}, request: {}, and error: {}", id,
          request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
