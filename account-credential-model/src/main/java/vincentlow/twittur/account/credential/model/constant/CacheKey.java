package vincentlow.twittur.account.credential.model.constant;

public interface CacheKey {

  String BASE_KEY = "vincentlow.twittur.account.credential:";

  String FIND_ONE_ACCOUNT_CREDENTIAL = BASE_KEY + "findOneAccountCredential-%s"; // id

  String FIND_ONE_ACCOUNT_TOKEN = BASE_KEY + "findOneAccountToken-%s-%s"; // id-token

  // PATTERN
  String FIND_ONE_ACCOUNT_TOKEN_PATTERN = BASE_KEY + "findOneAccountToken-%s-*"; // id
}
