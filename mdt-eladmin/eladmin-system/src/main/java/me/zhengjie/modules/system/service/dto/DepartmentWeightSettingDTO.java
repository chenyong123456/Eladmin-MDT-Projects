package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;
/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
@Data
public class DepartmentWeightSettingDTO implements Serializable {
  private Long id;

  private Integer tumorId;

  private Long deptId;

  private Double weightings;
}
