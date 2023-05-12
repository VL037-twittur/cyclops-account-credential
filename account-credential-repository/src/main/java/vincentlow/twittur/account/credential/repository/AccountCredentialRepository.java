package vincentlow.twittur.account.credential.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vincentlow.twittur.account.credential.model.entity.AccountCredential;

@Repository
public interface AccountCredentialRepository extends JpaRepository<AccountCredential, String> {

  AccountCredential findByIdAndMarkForDeleteFalse(String id);

  AccountCredential findByUsernameAndMarkForDeleteFalse(String username);

  AccountCredential findByEmailAddressAndMarkForDeleteFalse(String emailAddress);

  @Query("SELECT ac FROM AccountCredential ac WHERE (ac.username = :username OR ac.emailAddress = :username) AND markForDelete = false")
  AccountCredential findByUsernameOrEmailAddressAndMarkForDeleteFalse(String username);

}
