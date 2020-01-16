package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.AuditInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuditInfoRepository extends JpaRepository<AuditInfo, Integer>, JpaSpecificationExecutor {
}
