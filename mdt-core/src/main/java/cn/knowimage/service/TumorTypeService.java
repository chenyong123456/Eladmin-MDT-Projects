package cn.knowimage.service;

import cn.knowimage.pojo.instance.TumorType;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 肿瘤类型业务接口
 * @author Liang Yuxuan
 * @date 2019.12.26
 */
public interface TumorTypeService {
  /**
   * 查询所有的肿瘤类型
   * @return 肿瘤类型集合
   */
  List<TumorType> getAllTumorType();

  /**
   * 新增肿瘤类型
   * @param tumorName 肿瘤名字
   * @param creator 创建人id
   */
  JSONObject addTumorType(String tumorName, String creator);

  /**
   * 删除肿瘤类型
   */
  void deleteTumorType(int tumorId);
}
