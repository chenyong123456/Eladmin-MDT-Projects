package cn.knowimage.service;

import java.util.List;

/**
 * 肿瘤类型对应的治疗方式业务接口
 * @author Liang Yuxuan
 * @date 2019.12.26
 */
public interface TumorTypeTreatmentService {
  /**
   * 新增肿瘤类型对应的治疗方式
   * @param tumorId 肿瘤id
   * @param creator 创建人id
   * @param checkList 治疗方式
   */
  void addAndUpdateTumorTypeTreatment(String tumorId, String creator, String checkList);
  /**
   * 查询所有的肿瘤类型对应的治疗方式
   * @return List<TumorTypeTreatmentPojo> 肿瘤类型对应的治疗方式集合
   */
   List<Integer> queryTreatmentByTumor(Integer tumorId);
  //List<TumorTypeTreatmentPojo> queryTreatmentByTumor(String tumorId);
}
