package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.Position;
import me.zhengjie.modules.system.service.dto.PositionDTO;
import me.zhengjie.modules.system.service.dto.PositionQueryCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Pageable;

/**
 * @author Liang Yuxuan
 * @date 2019-11-29
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PositionMapper extends EntityMapper<PositionDTO, Position>{
}
