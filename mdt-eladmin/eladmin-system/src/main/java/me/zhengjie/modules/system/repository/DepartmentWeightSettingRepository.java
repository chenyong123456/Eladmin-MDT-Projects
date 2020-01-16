package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
public interface DepartmentWeightSettingRepository extends JpaRepository<DepartmentWeightSetting, Long>, JpaSpecificationExecutor {
  /**
   * 根据科室id删除对应的权重设置信息
   * @param deptId 科室id
   * @return
   */
  @Transactional
  @Delete(value=" delete from DEPARTMENT_WEIGHT_SETTING  where department_id=?1")
  int deleteDepartmentWeightSettingByDeptId(Long deptId);

  /**
   * 根据科室id查看科室权重表的数据
   * @param deptId 科室id
   * @return List<DepartmentWeightSetting> 科室权重表的集合
   */
  @Query(value="select * from DEPARTMENT_WEIGHT_SETTING where department_id = ?1",nativeQuery = true)
  List<DepartmentWeightSetting> findByPid(Long deptId);
}
