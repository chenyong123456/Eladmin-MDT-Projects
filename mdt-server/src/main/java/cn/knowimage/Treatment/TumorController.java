package cn.knowimage.Treatment;

import cn.knowimage.pojo.instance.TumorType;
import cn.knowimage.service.TumorTypeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 肿瘤类型控制类
 * @author Liang Yuxuan
 * @date 2019.12.26
 */
@Controller
@CrossOrigin
@RequestMapping("api")
public class TumorController {
  @Autowired
  TumorTypeService tumorTypeService;

  /**
   * 查询所有的肿瘤类型
   * @return List<TumorType> 肿瘤类型集合
   */
  @RequestMapping("queryAllTumor")
  @ResponseBody
  public List<TumorType> queryAll(){
    List<TumorType> list = tumorTypeService.getAllTumorType();
    return list;
  }

  /**
   * 新增肿瘤类型
   * @param tumorName 肿瘤名字
   * @param creator 创建人
   */
  @PostMapping("addTumorType")
  @ResponseBody
  public JSONObject addTumorType(String tumorName, String creator){
   return tumorTypeService.addTumorType(tumorName,creator);
  }

  /**
   * 根据id删除肿瘤类型
   * @param tumorId 肿瘤id
   */
  @PostMapping("deleteTumorTypeById")
  @ResponseBody
  public void deleteTumorTypeById(String tumorId){
    System.out.println("tumorId===================>"+tumorId);
    //删除肿瘤类型
    tumorTypeService.deleteTumorType(Integer.parseInt(tumorId));


  }



}
