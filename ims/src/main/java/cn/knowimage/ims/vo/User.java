package cn.knowimage.ims.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "EL_USER")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String avatar;  // 用户的图像

    private Date create_time;

    private String email;

    private Integer enabled;

    private String password;

    private String username;

    private String other_contacts;

    private Date last_password_reset_time;

    private Integer dept_id;

    private String phone;

    private String invitationcode;

    private String creator;

    private String modifier;

    private Date modiftime;

    private Integer job_id;  // 科室id

    private String wx;

    private Integer position_id;

    private Integer sex;

    private Integer age;

    // 微信图像
    @Column(name = "wx_avatar")
    private String wx_avatar;

    // 科室的name
    @Transient
    private String name;
}
