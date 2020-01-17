package cn.knowimage.weight;


import cn.knowimage.service.DepartmentService;
import cn.knowimage.service.DepartmentWeightSettingService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * 科室控制层
 * @author Liang Yuxuan
 * @date 2020/01/03
 */
@Controller
@CrossOrigin
@RequestMapping("api")
public class WeightController {
  @Autowired
  DepartmentWeightSettingService departmentWeightSettingService;
  @Autowired
  DepartmentService departmentService;
  @ResponseBody
  @GetMapping("queryDepartmentWeightByTumor")
  public JSONObject queryDepartmentWeightByTumor(){
    return departmentWeightSettingService.queryDepartmentWeightByTumor();

  }

  /**
   * 修改科室权重
   * @param weightData
   */
  @Transactional(rollbackFor = Exception.class)
  @ResponseBody
  @PostMapping("updateDeptWeightByTumor")
  public void updateDeptWeightByTumor(@RequestParam(value="form") String weightData){
    departmentWeightSettingService.updateDeptWeightByTumor(weightData);

  }

}
