package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "LIVING_HABBIT")
public class LivingHistory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private Integer drinking_history;

    private Integer smoking_history;

    private Integer mdt_id;

}
