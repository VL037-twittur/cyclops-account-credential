package vincentlow.twittur.account.credential.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.account.credential.model.constant.ApiPath;
import vincentlow.twittur.account.credential.service.AuthenticationService;
import vincentlow.twittur.account.credential.web.model.request.CreateAccountRequest;
import vincentlow.twittur.account.credential.web.model.request.LoginRequest;
import vincentlow.twittur.account.credential.web.model.response.AuthenticationResponse;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;

@Slf4j
@RestController
@RequestMapping(ApiPath.AUTHENTICATION)
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<ApiSingleResponse<AuthenticationResponse>> register(
      @RequestBody CreateAccountRequest request) {

    try {
      AuthenticationResponse response = authenticationService.register(request);
      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#AuthenticationController#register ERROR! with request: {}, and error: {}", request, e.getMessage(),
          e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<ApiSingleResponse<AuthenticationResponse>> login(@RequestBody LoginRequest request) {

    try {
      AuthenticationResponse response = authenticationService.login(request);
      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#AuthenticationController#login ERROR! with request: {}, and error: {}", request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ApiSingleResponse<AuthenticationResponse>> refreshToken(HttpServletRequest request) {

    try {
      AuthenticationResponse response = authenticationService.refreshToken(request);
      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#AuthenticationController#refreshToken ERROR! with request: {}, and error: {}", request,
          e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
