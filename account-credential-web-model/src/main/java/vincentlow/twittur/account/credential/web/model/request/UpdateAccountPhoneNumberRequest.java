package vincentlow.twittur.account.credential.web.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountPhoneNumberRequest implements Serializable {

  private static final long serialVersionUID = 1742974303828086822L;

  private String phoneNumber;
}
