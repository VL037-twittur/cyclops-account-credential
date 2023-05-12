package vincentlow.twittur.account.credential.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfileResponse {

  private String username;

  private String accountName;

  private String bio;

  private int tweetsCount;

  private int followersCount;

  private int followingCount;
}
