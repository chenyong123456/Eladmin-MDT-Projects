package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.DepartmentWeightSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface DepartmentWeightSettingMapper extends Mapper<DepartmentWeightSetting> {
  /**
   * 根据肿瘤id查询权重
   * @param tumorId
   * @return
   */
  @Select("select weightings from DEPARTMENT_WEIGHT_SETTING where tumor_id=#{tumorId}")
  List<Double> queryWeightingsByTumor(int tumorId);

  /**
   * 根据肿瘤id，科室id查看权重
   * @param tumorId
   * @param deptId
   * @return
   */
  @Select("select weightings from DEPARTMENT_WEIGHT_SETTING where tumor_id=#{tumorId} and department_id=#{deptId}")
  Double queryWeightingsByTumorAndDept(@Param("tumorId") int tumorId, @Param("deptId") int deptId);
  /**
   * 根据肿瘤id查询科室id
   * @param tumorId
   * @return
   */
  @Select("select department_id from DEPARTMENT_WEIGHT_SETTING where tumor_id=#{tumor_id}")
  List<Integer> queryDeptIdByTumor(int tumorId);
  /**
   * 根据科室和肿瘤id修改权重
   * @param departmentWeightSetting 投票权重设置对象
   * @return
   */
  @Update("update DEPARTMENT_WEIGHT_SETTING set weightings=#{weightings} where tumor_id=#{tumorId} and department_id=#{departmentId}")
  int updateDeptWeightByTumor(DepartmentWeightSetting departmentWeightSetting);
}
