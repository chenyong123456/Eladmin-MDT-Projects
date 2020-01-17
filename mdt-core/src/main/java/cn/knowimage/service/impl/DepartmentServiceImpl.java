package cn.knowimage.service.impl;

import cn.knowimage.mapper.DepartmentMapper;
import cn.knowimage.pojo.instance.Department;
import cn.knowimage.service.DepartmentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科室业务层
 * @author Liang Yuxuan
 * @date 2020/01/03
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
  @Autowired
  DepartmentMapper departmentMapper;


  @Override
  public JSONArray queryAllDepartment() {

    JSONObject jsonObject = new JSONObject();
    JSONArray departArray = new JSONArray();
    List<Department> departmentList = departmentMapper.queryAllDepartment();
    for (Department dept:departmentList
    ) {
      jsonObject = new JSONObject();
      jsonObject.put("id",dept.getId());
      jsonObject.put("text",dept.getName());
      departArray.add(jsonObject);
    }
    System.out.println("科室长度================》"+departmentList.size());
    return departArray;
  }


}
