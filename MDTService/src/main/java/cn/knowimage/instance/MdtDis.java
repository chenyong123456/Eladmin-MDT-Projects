package cn.knowimage.instance;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mdt_dis")
public class MdtDis {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "mdt_id")
    private Integer mdtId;

    @Column(name = "DISEASE")
    private String disease;

    @Column(name = "TUMOR_ID")
    private Integer tumor_type_id;

}
