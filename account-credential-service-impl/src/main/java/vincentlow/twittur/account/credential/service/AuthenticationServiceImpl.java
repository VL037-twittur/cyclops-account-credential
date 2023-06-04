package vincentlow.twittur.account.credential.service;

import static vincentlow.twittur.account.credential.service.helper.ValidatorHelper.isPhoneNumberValid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.account.credential.client.AccountProfileFeignClient;
import vincentlow.twittur.account.credential.client.InformationFeignClient;
import vincentlow.twittur.account.credential.client.model.request.CreateAccountProfileRequest;
import vincentlow.twittur.account.credential.client.model.request.EmailRequest;
import vincentlow.twittur.account.credential.model.constant.EmailTemplateCode;
import vincentlow.twittur.account.credential.model.constant.ErrorCode;
import vincentlow.twittur.account.credential.model.constant.ExceptionMessage;
import vincentlow.twittur.account.credential.model.constant.Role;
import vincentlow.twittur.account.credential.model.constant.TokenType;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;
import vincentlow.twittur.account.credential.repository.AccountCredentialRepository;
import vincentlow.twittur.account.credential.repository.TokenRepository;
import vincentlow.twittur.account.credential.util.StringUtil;
import vincentlow.twittur.account.credential.util.ValidatorUtil;
import vincentlow.twittur.account.credential.web.model.request.CreateAccountRequest;
import vincentlow.twittur.account.credential.web.model.request.LoginRequest;
import vincentlow.twittur.account.credential.web.model.response.AuthenticationResponse;
import vincentlow.twittur.account.credential.web.model.response.exception.ConflictException;
import vincentlow.twittur.account.credential.web.model.response.exception.ForbiddenException;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final String STARTS_WITH_BEARER = "Bearer "; // including space

  @Autowired
  private AccountCredentialRepository accountCredentialRepository;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private InformationFeignClient informationFeignClient;

  // @Autowired
  // private AccountProfileFeignClientService accountProfileFeignClientService;

  @Autowired
  private AccountProfileFeignClient accountProfileFeignClient;

  @Override
  @Transactional
  public AuthenticationResponse register(CreateAccountRequest request) {

    StringUtil.trimStrings(request);
    validateRequest(request);

    AccountCredential existingAccount =
        accountCredentialRepository.findByUsernameAndMarkForDeleteFalse(request.getUsername());
    if (Objects.nonNull(existingAccount)) {
      throw new ConflictException(ExceptionMessage.USERNAME_IS_TAKEN);
    }

    existingAccount = accountCredentialRepository.findByEmailAddressAndMarkForDeleteFalse(request.getEmailAddress());
    if (Objects.nonNull(existingAccount)) {
      throw new ConflictException(ExceptionMessage.EMAIL_IS_ASSOCIATED_WITH_AN_ACCOUNT);
    }

    AccountCredential accountCredential = new AccountCredential();
    BeanUtils.copyProperties(request, accountCredential);

    accountCredential.setPassword(passwordEncoder.encode(request.getPassword()));
    accountCredential.setRole(Role.USER);
    accountCredential.setTokens(Collections.EMPTY_LIST);

    LocalDateTime now = LocalDateTime.now();
    accountCredential.setCreatedBy("system");
    accountCredential.setCreatedDate(now);
    accountCredential.setUpdatedBy("system");
    accountCredential.setUpdatedDate(now);

    accountCredentialRepository.save(accountCredential);

    CreateAccountProfileRequest createAccountProfileRequest = CreateAccountProfileRequest
        .builder()
        .accountCredentialId(accountCredential.getId())
        .build();
    BeanUtils.copyProperties(request, createAccountProfileRequest);
    BeanUtils.copyProperties(accountCredential, createAccountProfileRequest);

    accountProfileFeignClient.createAccountProfile(createAccountProfileRequest);

    String accessToken = jwtService.generateAccessToken(accountCredential);
    String refreshToken = jwtService.generateRefreshToken(accountCredential);

    saveAccountToken(accountCredential, accessToken, now);

    EmailRequest emailRequest = EmailRequest.builder()
        .recipient(accountCredential.getEmailAddress())
        .templateCode(EmailTemplateCode.AFTER_REGISTRATION)
        .username(request.getUsername())
        .build();

    informationFeignClient.sendEmail(emailRequest);

    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthenticationResponse login(LoginRequest request) {

    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

      AccountCredential account =
          accountCredentialRepository.findByUsernameOrEmailAddressAndMarkForDeleteFalse(request.getUsername());
      String accessToken = jwtService.generateAccessToken(account);
      String refreshToken = jwtService.generateRefreshToken(account);

      revokeAllAccountToken(account);
      saveAccountToken(account, accessToken, LocalDateTime.now());

      return AuthenticationResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    } catch (AuthenticationException e) {
      log.error("#login ERROR! with request: {}, and error: {}", request, e.getMessage(), e);
      throw new ForbiddenException(e.getMessage());
    }
  }

  @Override
  public AuthenticationResponse refreshToken(HttpServletRequest request) {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Objects.nonNull(authHeader) && authHeader.startsWith(STARTS_WITH_BEARER)) {
      String refreshToken = authHeader.substring(STARTS_WITH_BEARER.length());
      String username = jwtService.extractUsername(refreshToken);

      if (Objects.nonNull(username)) {
        AccountCredential account =
            accountCredentialRepository.findByIdAndMarkForDeleteFalse(username);

        if (jwtService.isTokenValid(refreshToken, account)) {
          String accessToken = jwtService.generateAccessToken(account);
          revokeAllAccountToken(account);
          saveAccountToken(account, accessToken, LocalDateTime.now());

          return AuthenticationResponse.builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .build();
        }
      }
    }
    throw new ForbiddenException(ExceptionMessage.AUTHENTICATION_FAILED);
  }

  private void validateRequest(CreateAccountRequest request) {

    ValidatorUtil.validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    ValidatorUtil.validateArgument(StringUtils.isNotBlank(request.getFirstName()),
        ErrorCode.FIRST_NAME_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getFirstName()
        .length() <= 50, ErrorCode.FIRST_NAME_MAXIMAL_LENGTH_IS_50.getMessage());
    ValidatorUtil.validateArgument(StringUtils.isNotBlank(request.getLastName()),
        ErrorCode.LAST_NAME_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getLastName()
        .length() <= 50, ErrorCode.LAST_NAME_MAXIMAL_LENGTH_IS_50.getMessage());
    ValidatorUtil.validateState(Objects.nonNull(request.getDateOfBirth()),
        ErrorCode.DATE_OF_BIRTH_MUST_NOT_BE_NULL.getMessage());
    ValidatorUtil.validateArgument(getCurrentAge(request.getDateOfBirth()) >= 13,
        ErrorCode.AGE_MUST_BE_AT_LEAST_13.getMessage());
    ValidatorUtil.validateArgument(StringUtils.isNotBlank(request.getUsername()),
        ErrorCode.USERNAME_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getUsername()
        .length() >= 5, ErrorCode.USERNAME_MINIMAL_LENGTH_IS_5.getMessage());
    ValidatorUtil.validateArgument(request.getUsername()
        .length() <= 15, ErrorCode.USERNAME_MAXIMAL_LENGTH_IS_15.getMessage());
    ValidatorUtil.validateArgument(validateBioLength(request.getBio()),
        ErrorCode.BIO_MAXIMAL_LENGTH_IS_100.getMessage());
    ValidatorUtil.validateArgument(StringUtils.isNotBlank(request.getEmailAddress()),
        ErrorCode.EMAIL_ADDRESS_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getEmailAddress()
        .length() <= 62, ErrorCode.EMAIL_ADDRESS_MAXIMAL_LENGTH_IS_62.getMessage());

    if (StringUtils.isNotBlank(request.getPhoneNumber())) {
      ValidatorUtil.validateArgument(isPhoneNumberValid(request.getPhoneNumber()),
          ErrorCode.PHONE_NUMBER_IS_NOT_VALID.getMessage());
    } else {
      request.setPhoneNumber(null);
    }

    ValidatorUtil.validateState(StringUtils.isNotBlank(request.getPassword()),
        ErrorCode.PASSWORD_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getPassword()
        .length() >= 10, ErrorCode.PASSWORD_MINIMAL_LENGTH_IS_10.getMessage());
    ValidatorUtil.validateState(StringUtils.isNotBlank(request.getConfirmPassword()),
        ErrorCode.CONFIRM_PASSWORD_MUST_NOT_BE_BLANK.getMessage());
    ValidatorUtil.validateArgument(request.getPassword()
        .equals(request.getConfirmPassword()), ErrorCode.CONFIRM_PASSWORD_IS_DIFFERENT_WITH_PASSWORD.getMessage());
  }

  private int getCurrentAge(LocalDate dateOfBirth) {

    LocalDate currentDate = LocalDate.now();
    return Period.between(dateOfBirth, currentDate)
        .getYears();
  }

  private boolean validateBioLength(String bio) {

    if (Objects.isNull(bio) || bio.isBlank()) {
      return true;
    }
    return bio.length() < 100;
  }

  private void saveAccountToken(AccountCredential accountCredential, String jwtToken, LocalDateTime now) {

    Token token = new Token();
    token.setAccountCredential(accountCredential);
    token.setToken(jwtToken);
    token.setType(TokenType.BEARER);
    token.setCreatedBy("system");
    token.setCreatedDate(now);
    token.setUpdatedBy("system");
    token.setUpdatedDate(now);

    tokenRepository.save(token);
  }

  private void revokeAllAccountToken(AccountCredential accountCredential) { // Don't want multiple valid tokens

    List<Token> validAccountTokens = tokenRepository.findAllValidTokensByAccountId(accountCredential.getId());
    if (!validAccountTokens.isEmpty()) {
      validAccountTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
      });
      tokenRepository.saveAll(validAccountTokens);
    }
  }
}
