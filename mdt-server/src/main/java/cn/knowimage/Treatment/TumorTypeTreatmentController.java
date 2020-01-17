package cn.knowimage.Treatment;

import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.service.TreatmentTypeService;
import cn.knowimage.service.TumorTypeTreatmentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 肿瘤类型治疗方式控制类
 * @author Liang Yuxuan
 * @date 2019.12.27
 */
@CrossOrigin
@Controller
@RequestMapping("/api")
public class TumorTypeTreatmentController {
  @Autowired
  TumorTypeTreatmentService tumorTypeTreatmentService;
  @Autowired
  TreatmentTypeService treatmentTypeService;

  @PostMapping("manageTumorTypeTreatment")
  @ResponseBody
  public void addAndUpdateTumorTypeTreatment(String tumorId,String creator,String checkList){
    System.out.println("tumorid==========>"+tumorId);
    System.out.println("creator==========>"+creator);
    System.out.println("checkList=============>"+checkList);
    tumorTypeTreatmentService.addAndUpdateTumorTypeTreatment(tumorId,creator,checkList);
  }

  /**
   * 查询所有的肿瘤类型对应的治疗方式
   * @return JSONObject 肿瘤类型对应的治疗方式集合
   */
 // @RequestMapping(value = "/queryTreatmentByTumor",method =RequestMethod.GET)
  @GetMapping("queryTreatmentByTumor")
  @ResponseBody
  public JSONObject queryTreatmentByTumor(String tumorId) {
    System.out.println("tumorId==============>"+tumorId);
    JSONArray jsonTreatment = new JSONArray();
    JSONObject resultJson = new JSONObject();
    //查询出所有的治疗方式
    List<TreatmentType> typeList = treatmentTypeService.queryAllTreatment();
    List<Integer> list = tumorTypeTreatmentService.queryTreatmentByTumor(Integer.parseInt(tumorId));
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
}
