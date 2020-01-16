package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import me.zhengjie.modules.system.repository.DepartmentWeightSettingRepository;
import me.zhengjie.modules.system.service.DepartmentWeightSettingService;
import me.zhengjie.modules.system.service.dto.DepartmentWeightSettingDTO;
import me.zhengjie.modules.system.service.mapper.DepartmentWeightSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
public class DepartmentWeightSettingServiceImpl implements DepartmentWeightSettingService {
  @Autowired
  DepartmentWeightSettingRepository departmentWeightSettingRepository;
  @Autowired
  DepartmentWeightSettingMapper departmentWeightSettingMapper;
  @Override
  public DepartmentWeightSettingDTO create(DepartmentWeightSetting resources) {
    System.out.println("resources============================>"+resources.toString());
    return departmentWeightSettingMapper.toDto(departmentWeightSettingRepository.save(resources));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(Long id) {
    departmentWeightSettingRepository.deleteById(id);
  }
}
