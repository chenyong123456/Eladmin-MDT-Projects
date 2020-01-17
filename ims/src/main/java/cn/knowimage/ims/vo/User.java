package cn.knowimage.ims.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "EL_USER")
public class User {

    @Id
    private Integer id;

    private String username;

    private String phone;

    private String email;

    private Integer job_id;

    private Integer position_id;

    private String sex;



    @Transient
    private String password;

}
