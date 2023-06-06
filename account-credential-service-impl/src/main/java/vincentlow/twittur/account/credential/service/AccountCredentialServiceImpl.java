package vincentlow.twittur.account.credential.service;

import static vincentlow.twittur.account.credential.service.helper.ValidatorHelper.validateAccount;
import static vincentlow.twittur.account.credential.util.ValidatorUtil.validateArgument;
import static vincentlow.twittur.account.credential.util.ValidatorUtil.validateState;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import vincentlow.twittur.account.credential.model.constant.ErrorCode;
import vincentlow.twittur.account.credential.model.constant.ExceptionMessage;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.repository.service.AccountCredentialRepositoryService;
import vincentlow.twittur.account.credential.service.helper.ValidatorHelper;
import vincentlow.twittur.account.credential.util.StringUtil;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountEmailRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPasswordRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPhoneNumberRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountUsernameRequest;
import vincentlow.twittur.account.credential.web.model.response.exception.BadRequestException;
import vincentlow.twittur.account.credential.web.model.response.exception.ConflictException;

@Service
public class AccountCredentialServiceImpl implements AccountCredentialService {

  @Autowired
  private AccountCredentialRepositoryService accountCredentialRepositoryService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public AccountCredential getAccountCredentialByUsername(String username) {

    AccountCredential accountCredential =
        accountCredentialRepositoryService.findByUsernameAndMarkForDeleteFalse(username);
    return validateAccount(accountCredential, ExceptionMessage.ACCOUNT_NOT_FOUND);
  }

  @Override
  public AccountCredential getAccountCredentialById(String id) {

    AccountCredential accountCredential = accountCredentialRepositoryService.findByIdAndMarkForDeleteFalse(id);
    return validateAccount(accountCredential, ExceptionMessage.ACCOUNT_NOT_FOUND);
  }

  @Override
  public AccountCredential getAccountCredentialByEmailAddress(String emailAddress) {

    AccountCredential accountCredential =
        accountCredentialRepositoryService.findByEmailAddressAndMarkForDeleteFalse(emailAddress);
    return validateAccount(accountCredential, ExceptionMessage.ACCOUNT_NOT_FOUND);
  }

  @Override
  // @Transactional
  public void updateAccountUsernameById(String id, UpdateAccountUsernameRequest request) {

    AccountCredential accountCredential = accountCredentialRepositoryService.findByIdAndMarkForDeleteFalse(id);
    validateAccount(accountCredential, ExceptionMessage.ACCOUNT_NOT_FOUND);

    StringUtil.trimStrings(request);

    validateArgument(StringUtils.isNotBlank(request.getUsername()),
        ErrorCode.USERNAME_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(request.getUsername()
        .length() >= 5, ErrorCode.USERNAME_MINIMAL_LENGTH_IS_5.getMessage());
    validateArgument(request.getUsername()
        .length() <= 15, ErrorCode.USERNAME_MAXIMAL_LENGTH_IS_15.getMessage());

    if (accountCredential.getUsername()
        .equals(request.getUsername())) {
      throw new BadRequestException(ExceptionMessage.USERNAME_MUST_BE_DIFFERENT);
    }

    AccountCredential existingAccount =
        accountCredentialRepositoryService.findByUsernameAndMarkForDeleteFalse(request.getUsername());
    if (Objects.nonNull(existingAccount)) {
      throw new ConflictException(ExceptionMessage.USERNAME_IS_TAKEN);
    }

    LocalDateTime now = LocalDateTime.now();
    accountCredential.setUsername(request.getUsername());
    accountCredential.setUpdatedBy(accountCredential.getId());
    accountCredential.setUpdatedDate(now);

    accountCredentialRepositoryService.save(accountCredential);
  }

  @Override
  public void updateAccountEmailAddressById(String id, UpdateAccountEmailRequest request) {

    AccountCredential account = accountCredentialRepositoryService.findByIdAndMarkForDeleteFalse(id);
    validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);

    StringUtil.trimStrings(request);

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getEmailAddress()),
        ErrorCode.EMAIL_ADDRESS_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(request.getEmailAddress()
        .length() <= 62, ErrorCode.EMAIL_ADDRESS_MAXIMAL_LENGTH_IS_62.getMessage());

    if (account.getEmailAddress()
        .equals(request.getEmailAddress())) {
      throw new BadRequestException(ExceptionMessage.EMAIL_MUST_BE_DIFFERENT);
    }

    AccountCredential existingAccount =
        accountCredentialRepositoryService.findByEmailAddressAndMarkForDeleteFalse(request.getEmailAddress());
    if (Objects.nonNull(existingAccount)) {
      throw new ConflictException(ExceptionMessage.EMAIL_IS_ASSOCIATED_WITH_AN_ACCOUNT);
    }

    account.setEmailAddress(request.getEmailAddress());
    account.setUpdatedBy(account.getId());
    account.setUpdatedDate(LocalDateTime.now());

    accountCredentialRepositoryService.save(account);
  }

  @Override
  public void updateAccountPhoneNumberById(String id, UpdateAccountPhoneNumberRequest request) {

    AccountCredential account = accountCredentialRepositoryService.findByIdAndMarkForDeleteFalse(id);
    validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);

    StringUtil.trimStrings(request);

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    if (StringUtils.isNotBlank(request.getPhoneNumber())) {
      validateArgument(ValidatorHelper.isPhoneNumberValid(request.getPhoneNumber()),
          ErrorCode.PHONE_NUMBER_IS_NOT_VALID.getMessage());
    } else {
      request.setPhoneNumber(null);
    }

    account.setPhoneNumber(request.getPhoneNumber());
    account.setUpdatedBy(account.getId());
    account.setUpdatedDate(LocalDateTime.now());

    accountCredentialRepositoryService.save(account);
  }

  @Override
  public void updateAccountPasswordById(String id, UpdateAccountPasswordRequest request) {

    AccountCredential account = accountCredentialRepositoryService.findByIdAndMarkForDeleteFalse(id);
    validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);

    StringUtil.trimStrings(request);

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateState(StringUtils.isNotBlank(request.getOldPassword()),
        ErrorCode.OLD_PASSWORD_MUST_NOT_BE_BLANK.getMessage());
    validateState(StringUtils.isNotBlank(request.getNewPassword()),
        ErrorCode.NEW_PASSWORD_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(request.getNewPassword()
        .length() >= 10, ErrorCode.PASSWORD_MINIMAL_LENGTH_IS_10.getMessage());
    validateState(StringUtils.isNotBlank(request.getConfirmNewPassword()),
        ErrorCode.CONFIRM_PASSWORD_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(request.getNewPassword()
        .equals(request.getConfirmNewPassword()), ErrorCode.CONFIRM_PASSWORD_IS_DIFFERENT_WITH_PASSWORD.getMessage());
    validateArgument(passwordEncoder.matches(request.getOldPassword(), account.getPassword()),
        ErrorCode.OLD_PASSWORD_IS_WRONG.getMessage());

    account.setPassword(passwordEncoder.encode(request.getNewPassword()));
    account.setUpdatedBy(account.getId());
    account.setUpdatedDate(LocalDateTime.now());

    accountCredentialRepositoryService.save(account);
  }
}
