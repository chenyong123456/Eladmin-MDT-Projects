package cn.knowimage.instance;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mdt_tnm")
public class MdtTnm {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "MDT_ID")
    private Integer mdt_id;

    @Column(name = "TNM_ID")
    private Integer tnm_id;

    @Column(name = "USER_ID")
    private Integer user_id;

    @Column(name = "IF_ACCEPT")
    private Integer if_accept;

}
