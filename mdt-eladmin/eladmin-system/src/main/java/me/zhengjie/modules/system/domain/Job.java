package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Entity
@Data
@Table(name="EL_JOB")
public class Job implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotNull
    private Long sort;

 /*   @ManyToMany
    @JoinTable(name = "EL_JOB_BUSSINESSPERMISSION_UNUSED", joinColumns = {@JoinColumn(name = "job_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "bussinesspermission_id",referencedColumnName = "id")})
    private Set<Businesspermission> businesspermissions;*/
    /**
     * 状态
     */
    @Column(name = "enabled",nullable = false)
    @NotNull
    private Boolean enabled;
    /**
     * 护士长
     */
    @Column(name = "head_nurse")
    private String headNurse;

    /**
     * 邀请人（参加MDT的人）
     */
    @Column(name = "inviter",nullable = false)
    private Long inviter;

    /**
     * 联系方式
     */
    @Column(name = "contact_phone",nullable = false)
    private String contactPhone;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {}

    @Override
    public String toString() {
      return "Job{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", sort=" + sort +
        ", enabled=" + enabled +
        ", headNurse='" + headNurse + '\'' +
        ", inviter='" + inviter + '\'' +
        ", contactPhone='" + contactPhone + '\'' +
        ", dept=" + dept +
        ", createTime=" + createTime +
        '}';
    }
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long getId(){
//      return id;
//    }

}
