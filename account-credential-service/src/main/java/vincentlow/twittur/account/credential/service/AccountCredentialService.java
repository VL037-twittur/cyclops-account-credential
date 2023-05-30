package vincentlow.twittur.account.credential.service;

import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountEmailRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPasswordRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountPhoneNumberRequest;
import vincentlow.twittur.account.credential.web.model.request.UpdateAccountUsernameRequest;

public interface AccountCredentialService {

  AccountCredential getAccountCredentialByUsername(String username);

  AccountCredential getAccountCredentialById(String id);

  AccountCredential getAccountCredentialByEmailAddress(String emailAddress);

  void updateAccountUsernameById(String id, UpdateAccountUsernameRequest request);

  void updateAccountEmailAddressById(String id, UpdateAccountEmailRequest request);

  void updateAccountPhoneNumberById(String id, UpdateAccountPhoneNumberRequest request);

  void updateAccountPasswordById(String id, UpdateAccountPasswordRequest request);
}
