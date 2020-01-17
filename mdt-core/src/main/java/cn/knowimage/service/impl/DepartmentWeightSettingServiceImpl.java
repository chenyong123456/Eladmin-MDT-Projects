package cn.knowimage.service.impl;

import cn.knowimage.mapper.DepartmentMapper;
import cn.knowimage.mapper.DepartmentWeightSettingMapper;
import cn.knowimage.mapper.TumorTypeMapper;
import cn.knowimage.mapper.VotingSettingTypeMapper;
import cn.knowimage.pojo.instance.Department;
import cn.knowimage.pojo.instance.DepartmentWeightSetting;
import cn.knowimage.pojo.instance.TumorType;
import cn.knowimage.pojo.instance.VotingSettingType;
import cn.knowimage.service.DepartmentWeightSettingService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentWeightSettingServiceImpl implements DepartmentWeightSettingService {
  @Autowired
  TumorTypeMapper tumorTypeMapper;
  @Autowired
  DepartmentWeightSettingMapper departmentWeightSettingMapper;
  @Autowired
  DepartmentMapper departmentMapper;
  @Autowired
  VotingSettingTypeMapper votingSettingTypeMapper;
  @Override
  public JSONObject queryDepartmentWeightByTumor() {
    JSONObject DepartObject = new JSONObject();
    JSONArray departArray = new JSONArray();
    List<Department> departmentList = departmentMapper.queryAllDepartment();
    for (Department dept:departmentList
    ) {
      DepartObject = new JSONObject();
      DepartObject.put("id",dept.getId());
      DepartObject.put("text",dept.getName());
      departArray.add(DepartObject);
    }

    List<TumorType> tumorList = tumorTypeMapper.getAllTumorType();
    JSONArray jsonArray = new JSONArray();
    //存放
    JSONObject jsonObject = new JSONObject();
    //存放结果
    JSONObject result = new JSONObject();
      for (TumorType tumor:tumorList
      ) {
        jsonObject.put("id",tumor.getId());
        jsonObject.put("name",tumor.getName());
        //根据肿瘤id查询科室投票权重
        //List<Double>  weightList = departmentWeightSettingMapper.queryWeightingsByTumor(tumor.getId());
        //查询所有的科室
        List<Department> deptList = departmentMapper.queryAllDepartment();
        for (int j = 0; j < deptList.size(); j++) {
          int size = j+1;
          //根据肿瘤id以及科室id查询投票权重
          jsonObject.put("weight"+size, departmentWeightSettingMapper.queryWeightingsByTumorAndDept(tumor.getId(),deptList.get(j).getId()));
        }
        jsonArray.add(jsonObject);
    }

    //查询投票机制
    int votingMechanism = votingSettingTypeMapper.selectAll().get(0).getVotingMechanism();
    result.put("votingMechanism",votingMechanism);
    result.put("department",departArray);
    result.put("tumorOfWeightData",jsonArray);
    return result;
  }

  @Override
  public void updateDeptWeightByTumor(String weightData) {
    System.out.println("weightData=============>"+weightData);
   // DepartmentWeightSetting departmentWeightSetting = new DepartmentWeightSetting();
    JSONObject weightDataObject = JSONObject.fromObject(weightData);
    int votingMechanism = weightDataObject.getInt("votingMechanism");
    List<VotingSettingType> list = votingSettingTypeMapper.selectAll();
    //查询所有的科室
    List<Department> departmentList = departmentMapper.queryAllDepartment();
    System.out.println("votingMechanism=========<"+votingMechanism);
    if(votingMechanism!=list.get(0).getVotingMechanism()){
      //修改投票机制
      votingSettingTypeMapper.updateVotingMechanism(votingMechanism);
    }
    JSONArray deptWeightSettingArray = weightDataObject.getJSONArray("deptWeightSetting");
    for (int i = 0; i < deptWeightSettingArray.size(); i++) {
      int tumorId = deptWeightSettingArray.getJSONObject(i).getInt("id");
      //根据肿瘤id查询科室id
      List<Integer>  deptList = departmentWeightSettingMapper.queryDeptIdByTumor(tumorId);
      for (int j = 0; j < deptList.size(); j++) {
        int size = j+1;
        Double weight =deptWeightSettingArray.getJSONObject(i).getDouble("weight"+size);
        DepartmentWeightSetting setting = new DepartmentWeightSetting();
        setting.setDepartmentId(departmentList.get(j).getId());
        setting.setTumorId(tumorId);
        setting.setWeightings(weight);
        //根据部门id以及肿瘤id修改权重
        departmentWeightSettingMapper.updateDeptWeightByTumor(setting);
      }
    }
   // departmentWeightSettingMapper.updateDeptWeightByTumor(departmentWeightSetting);
  }
}
