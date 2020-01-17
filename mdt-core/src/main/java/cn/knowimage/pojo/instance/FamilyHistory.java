package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 家庭病史表的数据模型
 */
@Data
@Table(name = "FAMILY_HISTORY")
public class FamilyHistory {

    // 主键
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    // 病人姓名
    private String name;

    // 家庭史
    private String family_disease_history;

    // MDT的id
    private Integer mdt_id;
}
