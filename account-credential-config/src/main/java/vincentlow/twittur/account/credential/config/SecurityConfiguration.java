package vincentlow.twittur.account.credential.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import vincentlow.twittur.account.credential.config.jwt.JWTAuthenticationFilter;
import vincentlow.twittur.account.credential.model.constant.ApiPath;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private JWTAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(ApiPath.AUTHENTICATION + "/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        /*
         * if URL invoked, spring will execute LogoutHandler & do not delegate it to any of our controllers
         */
        .logoutRequestMatcher(new AntPathRequestMatcher(ApiPath.AUTHENTICATION + "/logout", HttpMethod.POST.name()))
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        .and()
        .formLogin()
        .disable();

    return http.build();
  }
}
