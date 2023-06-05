package vincentlow.twittur.account.credential.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;
import vincentlow.twittur.account.credential.repository.TokenRepository;

@Service
public class JWTServiceImpl implements JWTService {

  public static final String TOKEN_PREFIX = "Bearer ";

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  @Value("${jwt.access-token.expirationInMs}")
  private long accessTokenExpirationInMs;

  @Value("${jwt.refresh-token.expirationInMs}")
  private long refreshTokenExpirationInMs;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserDetailsService userDetailsService;

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

  @Override
  public boolean isTokenValid(HttpServletRequest request, String token) {

    if (Objects.nonNull(token) && token.startsWith(TOKEN_PREFIX)) {
      token = token.substring(TOKEN_PREFIX.length());
    }

    String username = this.extractUsername(token);

    if (Objects.nonNull(username)) {
      AccountCredential accountCredential = (AccountCredential) userDetailsService.loadUserByUsername(username);
      Token accountToken = tokenRepository.findByTokenAndExpiredFalseAndRevokedFalseAndMarkForDeleteFalse(token);

      if (Objects.nonNull(accountToken) && isUserToken(username, accountCredential)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(accountCredential, null, accountCredential.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
            .setAuthentication(authToken);
        return true;
      }
    }
    return false;
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

  private boolean isUserToken(String username, AccountCredential accountCredential) {

    return accountCredential.getId()
        .equals(username);
  }
}
