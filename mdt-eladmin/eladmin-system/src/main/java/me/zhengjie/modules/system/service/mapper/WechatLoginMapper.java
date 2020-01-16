package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.WechatLogin;
import me.zhengjie.modules.system.service.dto.WechatLoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WechatLoginMapper extends EntityMapper<WechatLoginDTO, WechatLogin> {
}
