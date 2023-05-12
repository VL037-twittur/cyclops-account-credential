package vincentlow.twittur.account.credential.web.model.request;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest implements Serializable {

  private static final long serialVersionUID = -7714081327480700378L;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  private String username;

  private String accountName;

  private String bio;

  private String emailAddress;

  private String phoneNumber;

  private String password;

  private String confirmPassword;
}
