package vincentlow.twittur.account.credential.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

  @Value("${cache.default.ttl}")
  private long DEFAULT_TTL;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void flushAll() {

    RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory()
        .getConnection();
    redisConnection.flushDb();
    redisConnection.close();
  }

  @Override
  public <T> T get(String key, TypeReference<T> typeRef) {

    try {
      String cacheData = stringRedisTemplate.opsForValue()
          .get(key);
      if (Objects.nonNull(cacheData)) {
        return objectMapper.readValue(cacheData, typeRef);
      }
    } catch (Exception e) {
      log.error("#CacheServiceImpl#get ERROR! with key: {}, typeRef: {} and error: {}", key, typeRef, e.getMessage(),
          e);
    }
    return null;
  }

  @Override
  public <T> void set(String key, T data, Long ttl) {

    try {
      if (Objects.nonNull(data)) {
        String valueString = this.objectMapper.writeValueAsString(data);
        stringRedisTemplate.opsForValue()
            .set(key, valueString, getTTL(ttl), TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      log.error("#CacheServiceImpl#set ERROR! with key: {}, data: {}, ttl: {} and error: {}", key, data, ttl,
          e.getMessage(), e);
    }
  }

  @Override
  public void deleteByPattern(String pattern) {

    try {
      Set<String> keys = stringRedisTemplate.keys(pattern);
      stringRedisTemplate.delete(keys);
    } catch (Exception e) {
      log.error("#CacheServiceImpl#deleteByPattern ERROR! with pattern: {}, and error: {}", pattern, e.getMessage(), e);
    }
  }

  private long getTTL(Long ttl) {

    return Optional.ofNullable(ttl)
        .orElse(DEFAULT_TTL);
  }
}
