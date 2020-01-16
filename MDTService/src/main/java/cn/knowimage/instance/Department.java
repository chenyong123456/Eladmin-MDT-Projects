package cn.knowimage.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "EL_JOB")
public class Department {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    private Integer enabled;

    private Date create_time;

    private Integer sort;

    private Integer dept_id;

    private Integer inviter;  // 该科室主导人id

    private String head_nurse;

    private String contact_phone;

}
