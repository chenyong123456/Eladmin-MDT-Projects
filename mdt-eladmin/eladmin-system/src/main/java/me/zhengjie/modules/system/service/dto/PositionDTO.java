package me.zhengjie.modules.system.service.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author Liang Yuxuan
 * @date 2019-11-29
 */
@Data
@NoArgsConstructor
public class PositionDTO implements Serializable {

  /**
   * ID
   */
  private Long id;

  private Long sort;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean enabled;

  /**
   * 创建日期
   */
  private Timestamp createTime;

  public PositionDTO(String name, Boolean enabled) {
    this.name = name;
    this.enabled = enabled;
  }
}
