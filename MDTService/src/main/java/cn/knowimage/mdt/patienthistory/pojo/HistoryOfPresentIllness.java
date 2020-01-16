package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "HISTORY_OF_PRESENT_ILLNESS")
public class HistoryOfPresentIllness {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String cardinal_symptom;

    private String concomitant_symptom;

    @Column(name = "tumour_classify")
    private String tumour_classify;

    private String occurrence_time;

    private String present_situation;

    private String treatment_situation;

    private Integer mdt_id;

}
