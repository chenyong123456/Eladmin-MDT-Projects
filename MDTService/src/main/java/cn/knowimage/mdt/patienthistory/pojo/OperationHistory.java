package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "OPERATION_HISTORY")
public class OperationHistory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String operation_time;

    private Integer operation_status;

    private String hospital_name;

    private String medication_situation;

    private Integer mdt_id;


}
