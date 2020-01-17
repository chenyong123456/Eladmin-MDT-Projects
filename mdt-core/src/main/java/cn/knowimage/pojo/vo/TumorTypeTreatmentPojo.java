package cn.knowimage.pojo.vo;

import lombok.Data;

/**
 * 肿瘤类型
 */
@Data
public class TumorTypeTreatmentPojo {
  /**
   * 肿瘤id
   */
  private Integer tumorId;
  /**
   * 肿瘤名字（关联肿瘤表）
   */
  private String tumorName;
  /**
   * 治疗类型id
   */
  private Integer treatmentId;
  /**
   * 治疗类型名字（关联治疗类型表）
   */
  private String treatmentName;



}
