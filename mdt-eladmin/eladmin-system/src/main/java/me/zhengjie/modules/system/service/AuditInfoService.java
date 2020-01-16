package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.service.dto.AuditInfoQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

@CacheConfig(cacheNames = "Audit_info")
public interface AuditInfoService {
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(AuditInfoQueryCriteria criteria, Pageable pageable);
}
