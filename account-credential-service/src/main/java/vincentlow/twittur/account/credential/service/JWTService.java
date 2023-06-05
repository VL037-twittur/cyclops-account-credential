package vincentlow.twittur.account.credential.service;

import jakarta.servlet.http.HttpServletRequest;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;

public interface JWTService {

  String extractUsername(String token);

  String generateAccessToken(AccountCredential accountCredential);

  String generateRefreshToken(AccountCredential accountCredential);

  boolean isTokenValid(String token, AccountCredential accountCredential);

  boolean isTokenValid(HttpServletRequest request, String token);
}
