package cn.knowimage.service.impl;

import cn.knowimage.mapper.DepartmentMapper;
import cn.knowimage.mapper.DepartmentWeightSettingMapper;
import cn.knowimage.mapper.TumorTypeMapper;
import cn.knowimage.mapper.TumorTypeTreatmentMapper;
import cn.knowimage.pojo.instance.Department;
import cn.knowimage.pojo.instance.DepartmentWeightSetting;
import cn.knowimage.pojo.instance.TumorType;
import cn.knowimage.pojo.instance.TumorTypeTreatment;
import cn.knowimage.service.TumorTypeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TumorTypeServiceImpl implements TumorTypeService {
  @Autowired
  TumorTypeMapper tumorTypeMapper;
  @Autowired
  TumorTypeTreatmentMapper tumorTypeTreatmentMapper;
  @Autowired
  DepartmentWeightSettingMapper departmentWeightSettingMapper;
  @Autowired
  DepartmentMapper departmentMapper;
  @Override
  public List<TumorType> getAllTumorType() {
    return tumorTypeMapper.getAllTumorType();
  }

  @Override
  public JSONObject addTumorType(String tumorName, String creator) {
    JSONObject jsonObject = new JSONObject();
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    String recordTime = simpleDateFormat.format(date);
    TumorType nameOfTumor = new TumorType();
    nameOfTumor.setName(tumorName);
    //数据库存在
    if( tumorTypeMapper.select(nameOfTumor).size()!=0){
      jsonObject = new JSONObject();
      jsonObject.put("status",400);
    }else{
      jsonObject = new JSONObject();
      TumorType type = new TumorType();
      type.setName(tumorName);
      type.setCreate_time(recordTime);
      type.setCreator(Integer.parseInt(creator));
      tumorTypeMapper.addTumorType(type);
      //新增该肿瘤每个科室对应的投票权重
      List<Department> list = departmentMapper.queryAllDepartment();
      for (Department dept:list
           ) {
        DepartmentWeightSetting departmentWeightSetting = new DepartmentWeightSetting();
        departmentWeightSetting.setTumorId(type.getId());
        departmentWeightSetting.setWeightings(Double.parseDouble("1"));
        departmentWeightSetting.setDepartmentId(dept.getId());
        departmentWeightSettingMapper.insert(departmentWeightSetting);
      }
      jsonObject.put("status",200);

    }
    return jsonObject;

  }

  @Override
  public void deleteTumorType(int tumorId) {
    //删除肿瘤类型
    tumorTypeMapper.deleteByPrimaryKey(tumorId);
    //删除肿瘤类型对应的治疗方式
    TumorTypeTreatment treatment=new TumorTypeTreatment();
    treatment.setTumorId(tumorId);
    tumorTypeTreatmentMapper.delete(treatment);
    //删除科室对应该肿瘤的权重
    DepartmentWeightSetting departmentWeightSetting = new DepartmentWeightSetting();
    departmentWeightSetting.setTumorId(tumorId);
    departmentWeightSettingMapper.delete(departmentWeightSetting);
  }


}
