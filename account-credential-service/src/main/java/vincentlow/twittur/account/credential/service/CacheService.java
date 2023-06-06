package vincentlow.twittur.account.credential.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface CacheService {

  void flushAll();

  <T> T get(String key, TypeReference<T> typeRef);

  <T> void set(String key, T data, Long ttl);

  void deleteByPattern(String pattern);
}
