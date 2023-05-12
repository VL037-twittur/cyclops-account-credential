package vincentlow.twittur.account.credential.service;

import vincentlow.twittur.account.credential.model.entity.AccountCredential;

public interface JWTService {

  String extractUsername(String token);

  String generateAccessToken(AccountCredential accountCredential);

  String generateRefreshToken(AccountCredential accountCredential);

  boolean isTokenValid(String token, AccountCredential accountCredential);
}
