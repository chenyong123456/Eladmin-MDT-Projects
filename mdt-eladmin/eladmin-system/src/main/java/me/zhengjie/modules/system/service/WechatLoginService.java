package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.WechatLogin;
import me.zhengjie.modules.system.service.dto.WechatLoginDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

public interface WechatLoginService {
  WechatLogin findOpenIdBySign(String sign);
  /**
   * create
   *
   * @param resources
   * @return
   */
  WechatLoginDTO create(WechatLogin resources);




}
