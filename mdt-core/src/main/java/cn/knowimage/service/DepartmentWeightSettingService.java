package cn.knowimage.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 肿瘤科室对应的投票权重
 */
@Service
public interface DepartmentWeightSettingService {
  /**
   * 根据肿瘤查询科室对应的投票权重
   * @return
   */
  JSONObject queryDepartmentWeightByTumor();

  void updateDeptWeightByTumor(String weightData);
}
