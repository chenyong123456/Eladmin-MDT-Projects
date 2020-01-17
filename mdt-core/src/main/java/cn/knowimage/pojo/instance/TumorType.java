package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对应相应的TUMOR_TYPE表
 */
@Data
@Table(name = "TUMOR_TYPE")
public class TumorType {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "modifier")
    private Integer modifier;

    @Column(name = "modify_time")
    private String modify_time;

}
