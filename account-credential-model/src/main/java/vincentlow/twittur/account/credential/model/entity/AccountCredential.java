package vincentlow.twittur.account.credential.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import vincentlow.twittur.account.credential.model.constant.Role;

@Entity
@Table(name = "account_credential")
@Data
public class AccountCredential extends BaseEntity implements UserDetails {

  private static final long serialVersionUID = -3737996310217815054L;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "email_address", unique = true)
  private String emailAddress;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "phone_number")
  private String phoneNumber;

  @JsonIgnore
  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonIgnore
  @OneToMany(mappedBy = "accountCredential") // 1 account have many tokens
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return true;
  }
}
