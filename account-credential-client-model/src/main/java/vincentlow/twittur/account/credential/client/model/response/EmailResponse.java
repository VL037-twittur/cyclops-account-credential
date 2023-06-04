package vincentlow.twittur.account.credential.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailResponse {

  private String recipient;

  private boolean sent;
}
