package me.zhengjie.modules.system.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
@Entity
@Getter
@Setter
@Table(name = "DEPARTMENT_WEIGHT_SETTING")
public class DepartmentWeightSetting implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = Update.class)
  @Column(name = "id")
  private Long id;

  @Column(name = "tumor_id")
  private Integer tumorId;

  @Column(name = "department_id")
  private Long deptId;

  private Double weightings;

  public @interface Update {}

  @Override
  public String toString() {
    return "DepartmentWeightSetting{" +
      "id=" + id +
      ", tumorId=" + tumorId +
      ", deptId=" + deptId +
      ", weightings=" + weightings +
      '}';
  }
}
