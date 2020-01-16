/*
package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "EL_JOB_BUSSINESSPERMISSION_UNUSED")
public class Businesspermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Businesspermission.Update.class})
    private Long id;

    @NotBlank
    private String name;

    */
/**
     * 上级类目
     *//*

    @NotNull
    @Column(name = "pid",nullable = false)
    private Long pid;

    @NotBlank
    private String alias;

    @JsonIgnore
    @ManyToMany(mappedBy = "businesspermissions")
    private Set<Job> jobs;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

   

    @Override
	public String toString() {
		return "Businesspermission [id=" + id + ", name=" + name + ", pid=" + pid + ", alias=" + alias + ", jobs="
				+ jobs + ", createTime=" + createTime + "]";
	}



	public interface Update{}
}
*/
