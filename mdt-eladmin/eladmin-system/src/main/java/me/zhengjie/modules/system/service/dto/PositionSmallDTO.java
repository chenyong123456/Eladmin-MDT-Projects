package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Liang Yuxuan
 * @date 2019-11-29
 */
@Data
@NoArgsConstructor
public class PositionSmallDTO implements Serializable {

  /**
   * ID
   */
  private Long id;

  /**
   * 名称
   */
  private String name;
}
