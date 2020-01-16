package me.zhengjie.modules.system.service.dto;

import lombok.Data;

@Data
public class TumorTypeDTO {
  private Integer id;
  /**
   * 肿瘤名字
   */
  private String name;
  /**
   * 肿瘤父级id
   */
  private Integer pid;
  /**
   * 排序
   */
  private Integer sort;
  /**
   * 创建者
   */
  private Integer creator;
  /**
   * 创建时间
   */
  private String createTime;
  /**
   * 修改者
   */
  private Integer modifier;
  /**
   * 修改时间
   */
  private String modifyTime;
}
