package vincentlow.twittur.account.credential.repository.service;

import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;

public interface TokenRepositoryService {

  Token findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(
      AccountCredential accountCredential, String token);

  void save(Token token);

  Token findByToken(String token);

  void deleteByAccountCredential(AccountCredential accountCredential);
}
