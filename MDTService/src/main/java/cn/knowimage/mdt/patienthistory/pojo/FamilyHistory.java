package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "FAMILY_HISTORY")
public class FamilyHistory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String family_disease_history;

    private Integer mdt_id;
}
