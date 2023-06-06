package vincentlow.twittur.account.credential.repository.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import vincentlow.twittur.account.credential.model.constant.CacheKey;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.repository.AccountCredentialRepository;
import vincentlow.twittur.account.credential.service.CacheService;

@Service
public class AccountCredentialRepositoryServiceImpl implements AccountCredentialRepositoryService {

  @Autowired
  private CacheService cacheService;

  @Autowired
  private AccountCredentialRepository accountCredentialRepository;

  @Override
  public AccountCredential findByUsernameOrEmailAddressAndMarkForDeleteFalse(String username) {

    return accountCredentialRepository.findByUsernameOrEmailAddressAndMarkForDeleteFalse(username);
  }

  @Override
  public AccountCredential findByIdAndMarkForDeleteFalse(String id) {

    String key = String.format(CacheKey.FIND_ONE_ACCOUNT_CREDENTIAL, id);
    AccountCredential accountCredential = cacheService.get(key, new TypeReference<>() {});
    if (Objects.isNull(accountCredential)) {
      accountCredential = accountCredentialRepository.findByIdAndMarkForDeleteFalse(id);
      cacheService.set(key, accountCredential, 3600L);
    }
    return accountCredential;
  }

  @Override
  public AccountCredential findByUsernameAndMarkForDeleteFalse(String username) {

    return accountCredentialRepository.findByUsernameAndMarkForDeleteFalse(username);
  }

  @Override
  public AccountCredential findByEmailAddressAndMarkForDeleteFalse(String emailAddress) {

    String key = String.format(CacheKey.FIND_ONE_ACCOUNT_CREDENTIAL, emailAddress);
    AccountCredential accountCredential = cacheService.get(key, new TypeReference<>() {});
    if (Objects.isNull(accountCredential)) {
      accountCredential = accountCredentialRepository.findByEmailAddressAndMarkForDeleteFalse(emailAddress);
      cacheService.set(key, accountCredential, 3600L);
    }
    return accountCredential;
  }

  @Override
  public void save(AccountCredential accountCredential) {

    accountCredentialRepository.save(accountCredential);
  }
}
