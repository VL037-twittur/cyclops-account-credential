package vincentlow.twittur.account.credential.repository.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import vincentlow.twittur.account.credential.model.constant.CacheKey;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;
import vincentlow.twittur.account.credential.repository.TokenRepository;
import vincentlow.twittur.account.credential.service.CacheService;

@Service
public class TokenRepositoryServiceImpl implements TokenRepositoryService {

  @Autowired
  private CacheService cacheService;

  @Autowired
  private TokenRepository tokenRepository;

  @Override
  public Token findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(AccountCredential accountCredential,
      String token) {

    String key = String.format(CacheKey.FIND_ONE_ACCOUNT_TOKEN, accountCredential.getId(), token);
    Token accountToken = cacheService.get(key, new TypeReference<>() {});
    if (Objects.isNull(accountToken)) {
      accountToken = tokenRepository.findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(token);
      cacheService.set(key, accountToken, 3600L);
    }
    return accountToken;
  }

  @Override
  public void save(Token token) {

    tokenRepository.save(token);
  }

  @Override
  public Token findByToken(String token) {

    return tokenRepository.findByToken(token);
  }

  @Override
  public void deleteByAccountCredential(AccountCredential accountCredential) {

    String key = String.format(CacheKey.FIND_ONE_ACCOUNT_TOKEN_PATTERN, accountCredential.getId());
    cacheService.deleteByPattern(key);
    tokenRepository.deleteByAccountCredential(accountCredential);
  }
}
