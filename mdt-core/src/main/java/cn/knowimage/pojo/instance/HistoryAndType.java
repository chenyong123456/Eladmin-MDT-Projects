package cn.knowimage.pojo.instance;

import lombok.Data;

/**
 * 操作史表和操作史类型
 */
@Data
public class HistoryAndType  {

    // 主键id
    private Integer id;

    // 病人的名称
    private String name;

    // 操作史时间
    private String operation_time;

    // 操作史的状态
    private Integer operation_status;

    // 住院名称
    private String hospital_name;

    // 用药情况
    private String medication_situation;

    // MDT的id
    private Integer mdt_id;

    // 手术类型
    private Integer is_operation_type;

    // 化学史类型
    private Integer is_chemotherapy_type;

    // 放射史类型
    private Integer is_radiotherapy_type;

    // 生物史类型
    private Integer is_biological_therapy;

    // 其治疗方式
    private String  other_therapy;

    // 手术史id
    private Integer operation_history_id;
}
