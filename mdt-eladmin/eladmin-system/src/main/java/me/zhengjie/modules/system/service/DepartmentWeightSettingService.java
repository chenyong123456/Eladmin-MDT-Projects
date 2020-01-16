package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.dto.DepartmentWeightSettingDTO;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
public interface DepartmentWeightSettingService {
  /**
   * create
   * @param resources
   * @return
   */
  @CacheEvict(allEntries = true)
  DepartmentWeightSettingDTO create(DepartmentWeightSetting resources);
  /**
   * delete
   * @param id
   */
  @CacheEvict(allEntries = true)
  void delete(Long id);
}
