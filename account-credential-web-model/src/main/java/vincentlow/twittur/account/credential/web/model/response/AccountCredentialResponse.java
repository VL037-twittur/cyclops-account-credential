package vincentlow.twittur.account.credential.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vincentlow.twittur.base.web.model.response.BaseResponse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentialResponse extends BaseResponse {

  private static final long serialVersionUID = 401212157050341485L;

  private String username;

  private String emailAddress;

  private String phoneNumber;

}
