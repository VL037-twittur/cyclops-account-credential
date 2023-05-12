package vincentlow.twittur.account.credential.model.constant;

public enum ErrorCode {

  REQUEST_MUST_NOT_BE_NULL("request must not be null"),

  // ACCOUNT
  FIRST_NAME_MUST_NOT_BE_BLANK("first name must not be blank"),
  FIRST_NAME_MAXIMAL_LENGTH_IS_50("first name maximal length is 50"),
  LAST_NAME_MUST_NOT_BE_BLANK("last name must not be blank"),
  LAST_NAME_MAXIMAL_LENGTH_IS_50("last name maximal length is 50"),
  DATE_OF_BIRTH_MUST_NOT_BE_NULL("date of birth must not be null"),
  AGE_MUST_BE_AT_LEAST_13("age must be at least 13"),
  USERNAME_MUST_NOT_BE_BLANK("username must not be blank"),
  USERNAME_MINIMAL_LENGTH_IS_5("username minimal length is 5"),
  USERNAME_MAXIMAL_LENGTH_IS_15("username maximal length is 15"),
  BIO_MAXIMAL_LENGTH_IS_100("bio maximal length is 100"),
  EMAIL_ADDRESS_MUST_NOT_BE_BLANK("email address must not be blank"),
  EMAIL_ADDRESS_MAXIMAL_LENGTH_IS_62("email address maximal length is 62"),
  PHONE_NUMBER_IS_NOT_VALID("phone number is not valid"),
  PASSWORD_MUST_NOT_BE_BLANK("password must not be blank"),
  OLD_PASSWORD_MUST_NOT_BE_BLANK("old password must not be blank"),
  OLD_PASSWORD_IS_WRONG("old password is wrong"),
  NEW_PASSWORD_MUST_NOT_BE_BLANK("new password must not be blank"),
  PASSWORD_MINIMAL_LENGTH_IS_10("password minimal length is 10"),
  CONFIRM_PASSWORD_MUST_NOT_BE_BLANK("confirm password must not be blank"),
  CONFIRM_PASSWORD_IS_DIFFERENT_WITH_PASSWORD("confirm password is different with password"),
  FOLLOWED_ID_MUST_NOT_BE_BLANK("followed id must not be blank"),
  FOLLOWER_ID_MUST_NOT_BE_BLANK("follower id must not be blank"),

  // TWEET
  MESSAGE_MUST_NOT_BE_BLANK("message must not be blank"),
  MESSAGE_MAXIMAL_LENGTH_IS_250("message maximal length is 250"),

  // EMAIL
  EMAIL_RECIPIENT_MUST_NOT_BE_BLANK("email recipient must not be blank"),
  EMAIL_SUBJECT_MUST_NOT_BE_BLANK("email recipient must not be blank"),
  EMAIL_BODY_MUST_NOT_BE_BLANK("email body must not be blank");

  private String message;

  ErrorCode(String message) {

    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
