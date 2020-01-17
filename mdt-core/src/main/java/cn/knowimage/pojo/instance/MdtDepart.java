package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mdt_depart")
public class MdtDepart {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer mdt_id;

    private Integer head_id;

    private Integer is_guiding;

}
