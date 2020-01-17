package cn.knowimage.service.impl;

import cn.knowimage.mapper.TreatmentPlanMapper;
import cn.knowimage.mapper.TreatmentTypeMapper;
import cn.knowimage.mapper.TumorTypeTreatmentMapper;
import cn.knowimage.pojo.instance.TreatmentPlan;
import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.pojo.instance.TumorTypeTreatment;
import cn.knowimage.service.TreatmentPlanService;
import cn.knowimage.service.TreatmentTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TreatmentPlanServiceImpl implements TreatmentPlanService {
  @Autowired
  TreatmentPlanMapper treatmentPlanMapper;
  @Autowired
  TreatmentPlanService treatmentPlanService;
  @Autowired
  TreatmentTypeService treatmentTypeService;
  @Autowired
  TreatmentTypeMapper treatmentTypeMapper;
  @Autowired
  TumorTypeTreatmentMapper tumorTypeTreatmentMapper;

  @Override
  public JSONObject addTreatment(String therapyMethod,String creator) {
    System.out.println("therapyMethod=========================>"+therapyMethod);
    HashSet<String> hashSetTreatmenName = new HashSet<String>();
    HashSet<String> hashSetTreatmenPlanName = new HashSet<String>();
    //定义标签 判断数据是否合理
    boolean flag = true;
    JSONObject jsonObject = new JSONObject();
    JSONArray therapy = JSONArray.fromObject(therapyMethod);
    List<TreatmentType> typeList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    Date date = new Date();
    String createTime = simpleDateFormat.format(date);
    /**
     * 循环遍历title添加到hashSet中，用于判断hashSet是否有重复值
     */
    for (int i = 0; i < therapy.size(); i++) {
      //类型名称
      String title = therapy.getJSONObject(i).getString("title");
      //将治疗方式名字添加到hashSet集合
      hashSetTreatmenName.add(title);
      JSONArray contentArray = therapy.getJSONObject(i).getJSONArray("content");
      System.out.println("OMG"+contentArray.size());
      hashSetTreatmenPlanName = new HashSet<>();
      for (int j = 0; j < contentArray.size(); j++) {
        if (contentArray.getJSONObject(j).getString("value") != null && !contentArray.getJSONObject(j).getString("value").equals("")) {
          String treatmentPlanName = contentArray.getJSONObject(j).getString("value");
          //将子条目添加到hashSet集合
          hashSetTreatmenPlanName.add(treatmentPlanName);
        }
      }
      //如果子条目没有重复值
      if(hashSetTreatmenPlanName.size()== contentArray.size()){

      }else{
        flag = false;
        //前端页面同一治疗方式存在相同的子条目
        jsonObject.put("status",401);
      }
    }
    if(hashSetTreatmenName.size()==therapy.size()){
      if(flag) {
        JSONArray frontTreatmentTypeArray = new JSONArray();
        JSONArray dbTreatmentTypeArray = new JSONArray();
        List<TreatmentType>  list = treatmentTypeMapper.selectAll();
        for (int j = 0; j < list.size(); j++) {
          dbTreatmentTypeArray.add(list.get(j).getName());
        }
        System.out.println("dbTreatmentTypeArray=======》"+dbTreatmentTypeArray.toString());
        for (int i = 0; i < therapy.size(); i++) {
          //类型名称
          String title = therapy.getJSONObject(i).getString("title");
          //添加到hashSet集合
          if (title != null && !title.equals("")) {
            TreatmentType type = new TreatmentType();
            type.setName(title);
            //根据治疗类型名字查询数据库中对应的数据
            TreatmentType treatmentTypeByName = treatmentTypeMapper.selectOne(type);
            frontTreatmentTypeArray.add(title);
            //如果对象查询到了,查询治疗方式表
            if(treatmentTypeByName!=null){
              TreatmentPlan planByTypeId = new TreatmentPlan();
              planByTypeId.setTreatmentTypeId(treatmentTypeByName.getId());
              //根据类型id查询治疗方式
              List<TreatmentPlan> treatmentPlanList = treatmentPlanMapper.select(planByTypeId);
              System.out.println("治疗方式集合长度==============================================》"+treatmentPlanList.size());
              //如果数据库中的治疗方式为空
              if(treatmentPlanList.size()== 0){
                //查询前端传过来的子条目
                JSONArray contentArray = therapy.getJSONObject(i).getJSONArray("content");
                System.out.println("前端传过来的子条目长度=======》"+contentArray.size());
                //如果前端传过来的子条目仍为0
                if(contentArray.size()==0){
                  System.out.println("=====================》前端传过来子条目为空");
                        //不做操作

                }
                //前端传过来的子条目不为空
                else{
                  System.out.println("=====================》前端传过来的子条目不为空");
                  for (int j = 0; j < contentArray.size(); j++) {
                    String treatmentPlanName = contentArray.getJSONObject(j).getString("value");
                      TreatmentPlan treatmentPlan = new TreatmentPlan(treatmentPlanName,treatmentTypeByName.getId(),Integer.parseInt(creator),createTime,null,null);
                      //新增治疗方式
                      treatmentPlanMapper.insert(treatmentPlan);
                  }
                }
              }
              //数据库中的治疗方式不为空
              else{

                //将数据中的治疗方式与前端传送过来的治疗方式做比较（1相同，不做操作，2不同，直接删除再添加，3有相同以及不同 同上）
                //遍历前端传过来的子条目作对比
                JSONArray contentArray = therapy.getJSONObject(i).getJSONArray("content");

                //如果前端传过来的子条目仍为0
                if(contentArray.size()==0){
                  TreatmentPlan treatmentPlan = new TreatmentPlan();
                  treatmentPlan.setTreatmentTypeId(treatmentTypeByName.getId());
                  //删除该类型下对应的子条目（治疗方式表）
                  System.out.println("做删除=======》"+contentArray.size());
                  treatmentPlanMapper.delete(treatmentPlan);
                }
                //前端传过来的子条目不为空
                else{
                  //定义第一个json数组
                  JSONArray jsonArray1 = new JSONArray();
                  JSONArray jsonArray2 = new JSONArray();
                  treatmentTypeByName.getName();
                  System.out.println("===========数组作比较==========》前端传过来的子条目不为空");
                  for (int j = 0; j < contentArray.size(); j++) {
                    String treatmentPlanName = contentArray.getJSONObject(j).getString("value");
                    jsonArray1.add(treatmentPlanName);

                  }
                  for (TreatmentPlan p:treatmentPlanList
                  ) {
                    jsonArray2.add(p.getName());
                  }

                  System.out.println("jsonArray1================>"+jsonArray1.toString());
                  System.out.println("jsonArray2================>"+jsonArray2.toString());
                  System.out.println(jsonArray1.toString());



                  //查询数据库中不同的方式
                  Set<String> it=getFistDiff(jsonArray1,jsonArray2);
                  //查询数据库对比前端传过来的不同的方式做删除操作
                  for (String dbDiff:it) {
                    TreatmentPlan treatmentPlan = new TreatmentPlan();
                    treatmentPlan.setName(dbDiff);
                    //删除该类型下对应的子条目（治疗方式表）
                    treatmentPlanMapper.delete(treatmentPlan);

                  }

                  Set<String> frontDiffSet=getFistDiff(jsonArray2,jsonArray1);
                  //查询出前端传过来的不在数据库中的治疗方式
                  for (String plan:frontDiffSet) {
                    //做新增治疗方式操作
                    TreatmentPlan treatmentPlan = new TreatmentPlan(plan,treatmentTypeByName.getId(),Integer.parseInt(creator),createTime,null,null);
                    treatmentPlanMapper.insert(treatmentPlan);
                    System.out.println("相同的治疗方式plan============》"+plan);
                    System.out.println( "   plan  "+plan);
                  }

                }

              }

            }
            //如果对象没有查询到,新增治疗类型&新增治疗方式
            else {
              System.out.println("对象没有查询到================");
              //将数据中的治疗方式与前端传送过来的治疗方式做比较（1相同，不做操作，2不同，直接删除再添加，3有相同以及不同 同上）
              TreatmentType treatmentType = new TreatmentType(title, Integer.parseInt(creator), createTime, null, null);
              //新增治疗类型
              typeList = treatmentTypeService.addTreatment(treatmentType);
              //新增类型下对应的治疗方式
              JSONArray contentArray = therapy.getJSONObject(i).getJSONArray("content");
              for (int j = 0; j < contentArray.size(); j++) {
                if (contentArray.getJSONObject(j).getString("value") != null && !contentArray.getJSONObject(j).getString("value").equals("")) {
                  String treatmentPlanName = contentArray.getJSONObject(j).getString("value");
                  for (TreatmentType t : typeList) {
                    TreatmentPlan treatmentPlan = new TreatmentPlan(treatmentPlanName,t.getId(),Integer.parseInt(creator),createTime,null,null);
                    //新增治疗方式
                    treatmentPlanMapper.insert(treatmentPlan);
                  }
                }
              }
            }

          }
        }
        //查询第2个数组中与第1个数组中的不同的值
        Set<String> set = getFistDiff(frontTreatmentTypeArray,dbTreatmentTypeArray);
        for (String it:set) {
          System.out.println("第一个数组中不存在的类型=======》"+it);
          TreatmentType treatmentType = new TreatmentType();
          treatmentType.setName(it);
          //根据名字查询类型id
          TreatmentType typeById = treatmentTypeMapper.selectOne(treatmentType);
          //删除治疗类型&对应类型下的治疗方式&删除对应肿瘤类型下的治疗方式
          treatmentTypeMapper.delete(treatmentType);
          //删除对应类型下的治疗方式
          TreatmentPlan treatmentPlan = new TreatmentPlan();
          treatmentPlan.setTreatmentTypeId(typeById.getId());
          treatmentPlanMapper.delete(treatmentPlan);
          //删除对应肿瘤类型下的治疗方式
          TumorTypeTreatment treatment = new TumorTypeTreatment();
          treatment.setTreatmentId(typeById.getId());
          tumorTypeTreatmentMapper.delete(treatment);

        }
      }else{
        return jsonObject;
      }

    }else{
      //界面中存在相同的治疗类型
      jsonObject.put("status",400);
    }
    return  jsonObject;
  }
  /**
   * Set 数组特性，返回数据库第一个数组中不存于第二个数组的字符串
   * @param a
   * @param b
   * @return
   */
  public static Set<String> getFistDiff(JSONArray a, JSONArray b) {
    // 用来存放数组2中不同的元素
    Set<String> diff = new HashSet<String>();
    // 用来存放数组a中的元素
    Set<String> temp = new HashSet<String>();
    for (int i = 0; i < a.size(); i++) {
      temp.add(a.getString(i));
    }
    for (int j = 0; j < b.size(); j++) {
      //把数组b中的元素添加到temp中
      // 把数组b中的元素放到Set中，可以去除重复的元素
      // 如果temp中已存在相同的元素，则temp.add（b[j]）返回false
      if (temp.add( b.getString(j))) {
        diff.add( b.getString(j));
      }
    }
    return diff;
  }
}
