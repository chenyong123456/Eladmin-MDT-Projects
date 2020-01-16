package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.AuditInfo;
import me.zhengjie.modules.system.service.dto.AuditInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditInfoMapper extends EntityMapper<AuditInfoDTO, AuditInfo> {
}
