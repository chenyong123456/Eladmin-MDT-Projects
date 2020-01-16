package me.zhengjie.modules.system.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 肿瘤类型实体类
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
@Entity
@Getter
@Setter
@Table(name = "TUMOR_TYPE")
public class TumorType implements Serializable {
  /**
   * 肿瘤id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull(groups = Update.class)
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
  @Column(name = "create_time")
  private String createTime;
  /**
   * 修改者
   */
  private Integer modifier;
  /**
   * 修改时间
   */
  @Column(name = "modify_time")
  private String modifyTime;
  public @interface Update {}
  public TumorType() {
  }

  public TumorType(String name, Integer pid, Integer sort, Integer creator, String createTime, Integer modifier, String modifyTime) {
    this.name = name;
    this.pid = pid;
    this.sort = sort;
    this.creator = creator;
    this.createTime = createTime;
    this.modifier = modifier;
    this.modifyTime = modifyTime;
  }

  @Override
  public String toString() {
    return "TumorType{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", pid=" + pid +
      ", sort=" + sort +
      ", creator=" + creator +
      ", createTime='" + createTime + '\'' +
      ", modifier=" + modifier +
      ", modifyTime='" + modifyTime + '\'' +
      '}';
  }
}
