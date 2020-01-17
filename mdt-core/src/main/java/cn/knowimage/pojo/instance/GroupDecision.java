package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "GROUP_DECISION")
public class GroupDecision {
    private  Integer id;
    @Column(name="tumor_id")
    private  Integer tumorId;
  /*  @Column(name="tumor_name")
    private  String tumorName;*/
    @Column(name="treatment_id")
    private  Integer treatmentId;
    @Column(name="mdt_id")
    private  Integer mdtId;
    private  Integer creator;
    @Column(name="create_time")
    private  String createTime;
    private  Double votes;
}
