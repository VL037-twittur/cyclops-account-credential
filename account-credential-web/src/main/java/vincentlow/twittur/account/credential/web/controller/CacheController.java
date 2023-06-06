package vincentlow.twittur.account.credential.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.account.credential.model.constant.ApiPath;
import vincentlow.twittur.account.credential.service.CacheService;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.api.ApiResponse;

@Slf4j
@RestController
@RequestMapping(value = ApiPath.CACHE)
public class CacheController extends BaseController {

  @Value("${spring.application.name}")
  private String appName;

  @Autowired
  private CacheService cacheService;

  @PostMapping("/flush-all")
  public ResponseEntity<ApiResponse> flushAllCache() {

    log.info("#CacheController#flushAllCache with appName: {}", appName);
    cacheService.flushAll();
    return toSuccessResponseEntity(successResponse);
  }
}
