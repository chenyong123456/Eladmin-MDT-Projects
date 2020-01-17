package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.Department;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface DepartmentMapper extends Mapper<Department> {
  /**
   * 查询id不为1&没有被禁用的科室
   * @return
   */
  @Select("select id,name from EL_JOB where id<>1 and enabled<>0")
  List<Department> queryAllDepartment();

}
