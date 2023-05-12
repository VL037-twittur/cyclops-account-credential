package vincentlow.twittur.account.credential.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;

@Service
public class JWTServiceImpl implements JWTService {

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  @Value("${jwt.access-token.expirationInMs}")
  private long accessTokenExpirationInMs;

  @Value("${jwt.refresh-token.expirationInMs}")
  private long refreshTokenExpirationInMs;

  @Override
  public String extractUsername(String token) {

    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String generateAccessToken(AccountCredential accountCredential) {

    return buildJWTToken(new HashMap<>(), accountCredential, accessTokenExpirationInMs);
  }

  @Override
  public String generateRefreshToken(AccountCredential accountCredential) {

    return buildJWTToken(new HashMap<>(), accountCredential, refreshTokenExpirationInMs);
  }

  @Override
  public boolean isTokenValid(String token, AccountCredential accountCredential) {

    String username = extractUsername(token);
    return username.equals(accountCredential.getId()) && !isTokenExpired(token);
  }

  private String buildJWTToken(Map<String, Object> extractClaims, AccountCredential accountCredential,
      long expiration) {

    return Jwts.builder()
        .setClaims(extractClaims)
        .setSubject(accountCredential.getId())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpired(String token) {

    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {

    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {

    byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
