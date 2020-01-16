package me.zhengjie.modules.system.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Liang Yuxuan
 * @date 2019-11-28
 */

@Entity
@Data
@Table(name="POSITION")
public class Position implements Serializable{
  /**
   * ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull(groups = Update.class)
  private Long id;

  /**
   * 名称
   */
  @Column(name = "name",nullable = false)
  @NotBlank
  private String name;

  @Column(unique = true)
  @NotNull
  private Long sort;

  /**
   * 状态
   */
  @Column(name = "enabled",nullable = false)
  @NotNull
  private Boolean enabled;

  /**
   * 创建日期
   */
  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  public @interface Update {}

  @Override
  public String toString() {
    return "Position{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", sort=" + sort +
      ", enabled=" + enabled +
      ", createTime=" + createTime +
      '}';
  }
}
