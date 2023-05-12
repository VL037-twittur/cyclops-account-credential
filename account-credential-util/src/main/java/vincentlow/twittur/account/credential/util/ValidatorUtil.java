package vincentlow.twittur.account.credential.util;

import vincentlow.twittur.account.credential.web.model.response.exception.BadRequestException;

public class ValidatorUtil {

  public static void validateArgument(boolean expression, String errorMessage) {

    if (!expression) {
      throw new BadRequestException(errorMessage);
    }
  }

  public static void validateState(boolean expression, String errorMessage) {

    if (!expression) {
      throw new BadRequestException(errorMessage);
    }
  }
}
