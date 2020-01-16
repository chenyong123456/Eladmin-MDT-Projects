package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.TumorType;
import me.zhengjie.modules.system.service.dto.TumorTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TumorTypeMapper extends EntityMapper<TumorTypeDTO, TumorType> {
}
