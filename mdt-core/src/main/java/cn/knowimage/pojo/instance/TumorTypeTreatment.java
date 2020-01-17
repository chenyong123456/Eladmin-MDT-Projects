package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 肿瘤类型对应的治疗方式实体类
 * @author Liang Yuxuan
 * @date 2019.12.26
 */
@Data
@Table(name = "TUMOR_TYPE_TREATMENT")
public class TumorTypeTreatment {
  /**
   * id
   */
  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;
  /**
   * 肿瘤id
   */
  @Column(name = "tumor_id")
  private Integer tumorId;
  /**
   * 治疗方式id
   */
  @Column(name = "treatment_id")
  private Integer treatmentId;
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

  public TumorTypeTreatment() {
  }

  public TumorTypeTreatment(Integer tumorId, Integer treatmentId, Integer creator, String createTime, Integer modifier, String modifyTime) {
    this.tumorId = tumorId;
    this.treatmentId = treatmentId;
    this.creator = creator;
    this.createTime = createTime;
    this.modifier = modifier;
    this.modifyTime = modifyTime;
  }

  @Override
  public String toString() {
    return "TumorTypeTreatment{" +
      "id=" + id +
      ", tumorId=" + tumorId +
      ", treatmentId=" + treatmentId +
      ", creator=" + creator +
      ", createTime='" + createTime + '\'' +
      ", modifier=" + modifier +
      ", modifyTime='" + modifyTime + '\'' +
      '}';
  }
}
