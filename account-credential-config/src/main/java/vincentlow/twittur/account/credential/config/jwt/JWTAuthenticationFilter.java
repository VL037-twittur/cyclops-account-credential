package vincentlow.twittur.account.credential.config.jwt;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vincentlow.twittur.account.credential.model.entity.AccountCredential;
import vincentlow.twittur.account.credential.model.entity.Token;
import vincentlow.twittur.account.credential.repository.TokenRepository;
import vincentlow.twittur.account.credential.service.JWTService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final String STARTS_WITH_BEARER = "Bearer "; // including space

  @Autowired
  private JWTService jwtService;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Objects.nonNull(authHeader) && authHeader.startsWith(STARTS_WITH_BEARER)) {
      String jwtToken = authHeader.substring(STARTS_WITH_BEARER.length());
      String username = jwtService.extractUsername(jwtToken);

      if (Objects.nonNull(username) &&
          Objects.isNull(SecurityContextHolder.getContext()
              .getAuthentication())) {
        AccountCredential accountCredential = (AccountCredential) userDetailsService.loadUserByUsername(username);
        boolean isTokenValid = isTokenValid(tokenRepository.findByToken(jwtToken));

        if (isTokenValid && jwtService.isTokenValid(jwtToken, accountCredential)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(accountCredential, null, accountCredential.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext()
              .setAuthentication(authToken);
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private boolean isTokenValid(Token token) {

    return Objects.nonNull(token) && !token.isExpired() && !token.isRevoked();
  }
}
