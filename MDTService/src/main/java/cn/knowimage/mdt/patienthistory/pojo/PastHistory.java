package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "PAST_HISTORY")
public class PastHistory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String past_history;

    private Integer is_hava_hypertension;

    private Integer is_hava_heart_disease;

    private Integer is_hava_diabetes;

    private Integer is_hava_tuberculosis;

    private Integer is_hava_hepatitis;

    private Integer mdt_id;

    private String other_past_history;
}
