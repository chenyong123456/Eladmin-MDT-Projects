package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.WechatLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WechatLoginRepository extends JpaRepository<WechatLogin, Long>, JpaSpecificationExecutor{
    WechatLogin findOpenIdBySign(String sign);
}
