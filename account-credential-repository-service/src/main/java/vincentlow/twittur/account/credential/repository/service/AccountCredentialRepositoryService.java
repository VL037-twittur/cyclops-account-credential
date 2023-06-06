package vincentlow.twittur.account.credential.repository.service;

import vincentlow.twittur.account.credential.model.entity.AccountCredential;

public interface AccountCredentialRepositoryService {

  AccountCredential findByUsernameOrEmailAddressAndMarkForDeleteFalse(String username);

  AccountCredential findByIdAndMarkForDeleteFalse(String id);

  AccountCredential findByUsernameAndMarkForDeleteFalse(String username);

  AccountCredential findByEmailAddressAndMarkForDeleteFalse(String emailAddress);

  void save(AccountCredential accountCredential);
}
