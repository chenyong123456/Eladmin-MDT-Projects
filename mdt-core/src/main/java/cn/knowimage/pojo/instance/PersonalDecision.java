package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "PERSONAL_DECISION")
public class PersonalDecision {

  private Integer id;
  @Column(name = "tumor_id")
  private Integer tumorId;
  @Column(name = "treatment_id")
  private Integer treatmentId;
  @Column(name = "mdt_id")
  private Integer mdtId;
  private String remark;
  private Integer creator;
  private String priority;



}
