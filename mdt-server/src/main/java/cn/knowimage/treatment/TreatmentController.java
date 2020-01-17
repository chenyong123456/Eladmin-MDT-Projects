package cn.knowimage.treatment;
import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.service.TreatmentPlanService;
import cn.knowimage.service.TreatmentTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 治疗方式控制类
 * @author Liang Yuxuan
 * @date 2019.12.25
 */
@Controller
@CrossOrigin
@RequestMapping("api")
public class TreatmentController {
  @Autowired
  private TreatmentPlanService treatmentPlanService;
  @Autowired
  private TreatmentTypeService treatmentTypeService;

  /**
   * 新增治疗方式
   * @param therapyMethod 治疗方式数据
   * @param creator 创建人
   * @return
   */
  @RequestMapping("addTreatment")
  @ResponseBody
  public JSONObject addTreatment(String therapyMethod,String creator) {
       return treatmentPlanService.addTreatment(therapyMethod,creator);
    }

  /**
   * 查询所有的治疗方式
   */
  @RequestMapping("queryAllTreatment")
  @ResponseBody
  public List<TreatmentType> queryAllTreatment() {
   return treatmentTypeService.queryAllTreatment();
  }

  /**
   * 查询所有的治疗类型&治疗方式
   */
  @RequestMapping("queryTreatments")
  @ResponseBody
  public JSONArray queryTreatments() {
    return treatmentTypeService.getTreatment();
  }

}
