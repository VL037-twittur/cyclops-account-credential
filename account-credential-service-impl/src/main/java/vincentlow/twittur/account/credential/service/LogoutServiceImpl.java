package vincentlow.twittur.account.credential.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vincentlow.twittur.account.credential.model.entity.Token;
import vincentlow.twittur.account.credential.repository.TokenRepository;

@Service
public class LogoutServiceImpl implements LogoutHandler {

  @Autowired
  private TokenRepository tokenRepository;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (Objects.nonNull(authHeader) && authHeader.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
      String jwtToken = authHeader.substring(JWTServiceImpl.TOKEN_PREFIX.length());
      Token token = tokenRepository.findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(jwtToken);

      if (Objects.nonNull(token)) {
        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
      }
    }
  }
}
