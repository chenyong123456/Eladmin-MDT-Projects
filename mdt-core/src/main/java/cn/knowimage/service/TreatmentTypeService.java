package cn.knowimage.service;

import cn.knowimage.pojo.instance.TreatmentType;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * 治疗类型业务接口
 * @author Liang Yuxuan
 * @date 2019.12.25
 */
public interface TreatmentTypeService {
  /**
   * 批量新增治疗类型并且取出最新的自增长id
   * @param treatmentType 治疗类型对象
   * @return List<TreatmentType> 显示的自增长id集合
   */
  List<TreatmentType> addTreatment(TreatmentType treatmentType);

  /**
   * 查询所有的治疗类型
   * @return
   */
  List<TreatmentType> queryAllTreatment();
  /**
   * 查询治疗类型&治疗方式
   * @return
   */
  JSONArray getTreatment();

  /**
   * 删除治疗类型&治疗方式
   */
  void deleteTreatment();
}
