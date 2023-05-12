package vincentlow.twittur.account.credential.service;

import jakarta.servlet.http.HttpServletRequest;
import vincentlow.twittur.account.credential.web.model.request.CreateAccountRequest;
import vincentlow.twittur.account.credential.web.model.request.LoginRequest;
import vincentlow.twittur.account.credential.web.model.response.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse register(CreateAccountRequest request);

  AuthenticationResponse login(LoginRequest request);

  AuthenticationResponse refreshToken(HttpServletRequest request);
}
