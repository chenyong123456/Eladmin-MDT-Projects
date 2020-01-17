package cn.knowimage.pojo.instance;

import cn.knowimage.ims.vo.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Data
@Table(name = "mdt")
public class MDT {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;  //MDT的id

    private String name;

    private Integer creator_id;

    private Integer is_repeat;

    private String goal;

    private String final_policy;

    private Date create_time;

    @Column(name = "PATIENT_ID")
    private Integer patient_id;

    @Transient
    private User patient;

    @Column(name = "GROUP_ID")
    private String group_id;

    @Column(name = "PREDICT_TIME")
    private String predict_time;

    @Column(name = "WAY")
    private String way;

    @Column(name = "PATIENT_NAME")
    private String patient_name;

    @Column(name = "URL_REPORT")
    private String url_report;

    @Column(name = "IS_SELF_PAYING")
    private Integer is_self_paying; // 是否自费（1是，2不是）

    @Column(name = "MEETING_LOCATION")
    private String meeting_location; // 拟会诊地点

    @Transient
    private Integer tnm_id;

    @Transient
    private Integer if_accept;


}
