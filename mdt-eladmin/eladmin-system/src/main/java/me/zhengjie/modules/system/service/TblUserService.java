package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.service.dto.TblUserQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;


@CacheConfig(cacheNames = "tbl_user")
public interface TblUserService {
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(TblUserQueryCriteria criteria, Pageable pageable);
}
