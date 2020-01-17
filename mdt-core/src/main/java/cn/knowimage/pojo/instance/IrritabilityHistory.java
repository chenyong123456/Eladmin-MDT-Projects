package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "IRRITABILITY_HISTORY")
public class IrritabilityHistory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String irritability_history;

    private Integer mdt_id;
}
