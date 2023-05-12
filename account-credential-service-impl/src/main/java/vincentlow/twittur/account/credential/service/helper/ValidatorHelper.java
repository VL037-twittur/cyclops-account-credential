package vincentlow.twittur.account.credential.service.helper;

import java.util.Objects;
import java.util.regex.Pattern;

import vincentlow.twittur.account.credential.web.model.response.exception.NotFoundException;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;

public class ValidatorHelper {

  public static AccountCredential validateAccount(AccountCredential account, String errorMessage) {

    if (Objects.isNull(account)) {
      throw new NotFoundException(errorMessage);
    }
    return account;
  }

  public static boolean isPhoneNumberValid(String phoneNumber) {

    String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(phoneNumber)
        .matches();
  }
}
