package vincentlow.twittur.account.credential.web.model.response.exception;

import org.springframework.security.core.AuthenticationException;

public class ForbiddenException extends AuthenticationException {

  public ForbiddenException(String message) {

    super(message);
  }
}
