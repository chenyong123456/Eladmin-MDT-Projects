package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "OPERATION_TYPE")
public class OperationType {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer is_operation_type;

    private Integer is_chemotherapy_type;

    private Integer is_radiotherapy_type;

    private Integer is_biological_therapy;

    private String  other_therapy;

    private String operation_briefly; // 手术简述

    private Integer operation_history_id;
}
