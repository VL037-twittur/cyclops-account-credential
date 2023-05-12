package vincentlow.twittur.account.credential.client.model.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountProfileRequest {

  private String accountCredentialId;

  private String username;

  private String accountName;

  private String bio;

  protected String createdBy;

  protected LocalDateTime createdDate;

  protected String updatedBy;

  protected LocalDateTime updatedDate;
}
