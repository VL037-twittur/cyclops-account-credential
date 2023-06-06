package vincentlow.twittur.account.credential.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

  @Query("SELECT t FROM Token t JOIN AccountCredential ac ON t.accountCredential.id = ac.id WHERE ac.id = :accountCredentialId AND (t.expired = false OR t.revoked = false)")
  List<Token> findAllValidTokensByAccountId(String accountCredentialId);

  Token findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(String token);

  Token findByToken(String token);

  @Transactional
  void deleteByAccountCredential(AccountCredential accountCredentialId);
}
