package vincentlow.twittur.account.credential.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

  private String recipient;

  private String templateCode;

  private String username;
}
