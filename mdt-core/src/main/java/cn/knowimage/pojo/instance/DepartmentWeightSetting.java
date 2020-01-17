package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 科室肿瘤权重设置实体类
 * @author Liang Yuxuan
 * @date 2020/01/03
 * */
@Data
@Table(name = "DEPARTMENT_WEIGHT_SETTING")
public class DepartmentWeightSetting {
  /**
   * ID
   */
  @Id
  @GeneratedValue(generator = "JDBC")
  private Long id;

  @Column(name = "tumor_id")
  private Integer tumorId;

  @Column(name = "department_id")
  private Integer departmentId;

  private Double weightings;
}
