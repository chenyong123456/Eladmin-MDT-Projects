package cn.knowimage.treatment;

import cn.knowimage.service.TreatmentTypeService;
import cn.knowimage.service.TumorTypeTreatmentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    return tumorTypeTreatmentService.queryTreatmentByTumor(Integer.parseInt(tumorId));
  }
}
