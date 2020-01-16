package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

@Data
public class HistoryAndType  {

    private Integer id;

    private String name;

    private String operation_time;

    private Integer operation_status;

    private String hospital_name;

    private String medication_situation;

    private Integer mdt_id;

    private Integer is_operation_type;

    private Integer is_chemotherapy_type;

    private Integer is_radiotherapy_type;

    private Integer is_biological_therapy;

    private String  other_therapy;

    private Integer operation_history_id;
}
