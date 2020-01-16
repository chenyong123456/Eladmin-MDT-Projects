package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@Table(name="audit_info")
public class AuditInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = User.Update.class)
    @Column(name = "pathway_id")
    private Integer pathwayId;

    @Column(name = "pathway_name")
    private String pathwayName;

    private String submitter;

    @Column(name = "data_upload_moment")
    private String dataUploadMoment;

    private String checker;
    @Column(name = "audit_state")
    private Integer auditState;


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "audit_time")
    private Date auditTime;

    @Column(name = "auditRemark")
    private String audit_remark;

    private Integer submitterid;

    private Integer checkerid;
}
