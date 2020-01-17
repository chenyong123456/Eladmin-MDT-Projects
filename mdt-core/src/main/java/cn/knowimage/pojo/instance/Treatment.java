package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mdt_dis")
public class Treatment {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer mdt_id;
    private String  varchar;
    private Integer tumor_id;
}
