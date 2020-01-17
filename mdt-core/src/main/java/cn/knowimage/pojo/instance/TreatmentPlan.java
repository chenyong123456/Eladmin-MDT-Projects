package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 治疗方式表
 * @author Liang Yuxuan
 * date 2019.12.25
 */
@Data
@Table(name = "TREATMENT_PLAN")
public class TreatmentPlan {
  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private String name;
  @Column(name = "treatment_type_id",nullable = false)
  private Integer treatmentTypeId;
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

  public TreatmentPlan() {
  }

  public TreatmentPlan(String name, Integer treatmentTypeId, Integer creator, String createTime, Integer modifier, String modifyTime) {
    this.name = name;
    this.treatmentTypeId = treatmentTypeId;
    this.creator = creator;
    this.createTime = createTime;
    this.modifier = modifier;
    this.modifyTime = modifyTime;
  }

  @Override
  public String toString() {
    return "TreatmentPlan{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", treatmentTypeId=" + treatmentTypeId +
      ", creator=" + creator +
      ", createTime='" + createTime + '\'' +
      ", modifier=" + modifier +
      ", modifyTime='" + modifyTime + '\'' +
      '}';
  }
}
