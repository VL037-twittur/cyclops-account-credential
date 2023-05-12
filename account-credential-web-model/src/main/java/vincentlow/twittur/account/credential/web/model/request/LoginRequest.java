package vincentlow.twittur.account.credential.web.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

  private static final long serialVersionUID = -4725145877994682071L;

  private String username;

  private String password;
}
