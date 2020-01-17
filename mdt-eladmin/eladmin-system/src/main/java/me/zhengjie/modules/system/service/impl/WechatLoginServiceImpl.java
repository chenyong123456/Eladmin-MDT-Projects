package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.WechatLogin;
import me.zhengjie.modules.system.repository.WechatLoginRepository;
import me.zhengjie.modules.system.service.WechatLoginService;
import me.zhengjie.modules.system.service.dto.WechatLoginDTO;
import me.zhengjie.modules.system.service.mapper.WechatLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WechatLoginServiceImpl implements WechatLoginService {
  @Autowired
  WechatLoginRepository wechatLoginRepository;
  @Autowired
  WechatLoginMapper wechatLoginMapper;

  @Override
  public WechatLogin findOpenIdBySign(String sign) {
    return wechatLoginRepository.findOpenIdBySign(sign);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public WechatLoginDTO create(WechatLogin resources) {

    return wechatLoginMapper.toDto(wechatLoginRepository.save(resources));
  }


//  @Override
//  @Transactional(rollbackFor = exception.class)
//  public JobDTO create(Job resources) {
//   // return jobMapper.toDto(jobRepository.save(resources));
//  }
}
