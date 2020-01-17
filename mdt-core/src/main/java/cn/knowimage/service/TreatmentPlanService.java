package cn.knowimage.service;

import net.sf.json.JSONObject;

/**
 * 治疗方式业务接口
 * @author Liang Yuxuan
 * @date 2019.12.25
 */
public interface TreatmentPlanService {
  /**
   * 新增治疗方案以及治疗类型
   * @param therapyMethod 前端传过来的治疗方案数据
   * @param creator 创建人id
   * @return JSONObject
   */
 JSONObject addTreatment(String therapyMethod, String creator);
}
