package cn.knowimage.service.impl;

import cn.knowimage.mapper.TreatmentPlanMapper;
import cn.knowimage.mapper.TreatmentTypeMapper;
import cn.knowimage.pojo.instance.TreatmentPlan;
import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.pojo.vo.TreatmentPojo;
import cn.knowimage.service.TreatmentTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 治疗方式业务层
 *
 * @author Liang Yuxuan
 * @date 2019.12.25
 */

@Service
public class TreatmentTypeServiceImpl implements TreatmentTypeService {
  @Autowired
  private TreatmentTypeMapper treatmentTypeMapper;
  @Autowired
  private TreatmentPlanMapper treatmentPlanMapper;

  @Transactional
  @Override
  public List<TreatmentType> addTreatment(TreatmentType treatmentType) {
    List<TreatmentType> treatmentTypeList = new ArrayList<>();
    int record = treatmentTypeMapper.insert(treatmentType);
    for (int i = 0; i < record; i++) {
      treatmentTypeList.add(treatmentType);
    }
    return treatmentTypeList;
  }

  @Override
  public List<TreatmentType> queryAllTreatment() {
    List<TreatmentType> list = treatmentTypeMapper.selectAll();
//    for (TreatmentType t:list) {
//      System.out.println("治疗方式名字--------------》"+t.getName());
//    }
    return list;
  }

  @Override
  public JSONArray getTreatment() {
    List<TreatmentPojo> list = treatmentTypeMapper.getTreatment();
   List<TreatmentType> treatmentList = treatmentTypeMapper.selectAll();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    for (TreatmentType t : treatmentList) {
      jsonObject.put("title",t.getName());
    //  Treatment
      TreatmentPlan plan = new TreatmentPlan();
      plan.setTreatmentTypeId(t.getId());
      List<TreatmentPlan> planList = treatmentPlanMapper.select(plan);
      JSONArray planArray = new JSONArray();
      //如果该类型id没有对应的治疗方式子项
      if(planList.size() != 0 ){
        for (TreatmentPlan p:planList
        ) {
          JSONObject planObject = new JSONObject();
          planObject.put("value",p.getName());
          planArray.add(planObject);
        }
      }
      jsonObject.put("content",planArray);
      jsonArray.add(jsonObject);

    }
    System.out.println(jsonArray.toString());

    return jsonArray;
  }

  @Override
  public void deleteTreatment() {

  }
}
