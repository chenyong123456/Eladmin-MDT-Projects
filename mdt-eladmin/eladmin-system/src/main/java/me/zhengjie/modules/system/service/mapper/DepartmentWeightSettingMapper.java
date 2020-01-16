package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.dto.DepartmentWeightSettingDTO;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentWeightSettingMapper extends EntityMapper<DepartmentWeightSettingDTO, DepartmentWeightSetting> {
}
