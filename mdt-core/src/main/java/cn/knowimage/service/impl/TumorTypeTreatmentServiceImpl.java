package cn.knowimage.service.impl;

import cn.knowimage.mapper.TumorTypeTreatmentMapper;
import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.pojo.instance.TumorTypeTreatment;
import cn.knowimage.service.TreatmentTypeService;
import cn.knowimage.service.TumorTypeTreatmentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TumorTypeTreatmentServiceImpl implements TumorTypeTreatmentService {
  @Autowired
  TumorTypeTreatmentMapper tumorTypeTreatmentMapper;
  @Autowired
  TumorTypeTreatmentService tumorTypeTreatmentService;
  @Autowired
  TreatmentTypeService treatmentTypeService;

  @Override
  public void addAndUpdateTumorTypeTreatment(String tumorId, String creator, String checkList) {
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    String createTime = simpleDateFormat.format(date);
    JSONArray jsonArray = JSONArray.fromObject(checkList);
    TumorTypeTreatment treatmentOfTumor = new TumorTypeTreatment();
    treatmentOfTumor.setTumorId(Integer.parseInt(tumorId));
    //根据肿瘤类型查询治疗地方时
    List<TumorTypeTreatment> treatments = tumorTypeTreatmentMapper.select(treatmentOfTumor);
    if(treatments.size()!=0){
      tumorTypeTreatmentMapper.delete(treatmentOfTumor);

    }
    for (int i = 0; i < jsonArray.size(); i++) {
      TumorTypeTreatment treatment = new TumorTypeTreatment(Integer.parseInt(tumorId),jsonArray.getInt(i),Integer.parseInt(creator),createTime,null,null);
      tumorTypeTreatmentMapper.insert(treatment);
    }

  }

  @Override
  public JSONObject queryTreatmentByTumor(Integer tumorId) {
    List<Integer> list = tumorTypeTreatmentMapper.queryTreatmentByTumor(tumorId);
    System.out.println("tumorId==============>"+tumorId);
    JSONArray jsonTreatment = new JSONArray();
    JSONObject resultJson = new JSONObject();
    //查询出所有的治疗方式
    List<TreatmentType> typeList = treatmentTypeService.queryAllTreatment();
    jsonTreatment = JSONArray.fromObject(typeList);
    //如果该肿瘤没有分配治疗方式
    if(list.size()==0){
      resultJson = new JSONObject();
      JSONArray jsonTumor = new JSONArray();
      resultJson.put("allTreatment",jsonTreatment);
      resultJson.put("tumorOfTreatment",jsonTumor);
    }else{
      resultJson = new JSONObject();
      JSONArray jsonTumor = new JSONArray();
      //根据肿瘤id查询治疗方式
      for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
        jsonTumor.add(list.get(i));
      }
      resultJson.put("allTreatment",jsonTreatment);
      resultJson.put("tumorOfTreatment",jsonTumor);
      //查询出该肿瘤对应的治疗方式

    }
    return resultJson;
  }
//
//  @Override
//  public JSONArray queryTreatmentByTumor(String tumorId) {
//    System.out.println("tumorId=============>"+tumorId);
//    JSONArray json = new JSONArray();
//    List<TumorTypeTreatmentPojo> list = tumorTypeTreatmentService.queryTreatmentByTumor(tumorId);
//   // System.out.println();
//    //如果该肿瘤没有分配治疗方式
//    if(list.size()==0){
//      //查询出所有的治疗方式
//      List<TreatmentType> typeList = treatmentTypeService.queryAllTreatment();
//      json = JSONArray.fromObject(typeList);
//      return json;
//    }else{
//      json = JSONArray.fromObject(list);
//      //查询出该肿瘤对应的治疗方式
//      return json;
//    }
//  }
}
