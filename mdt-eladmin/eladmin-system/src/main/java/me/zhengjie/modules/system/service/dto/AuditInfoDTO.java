package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class AuditInfoDTO implements Serializable {

    private Integer pathwayId;


    private String pathwayName;

    private String submitter;


    private String dataUploadMoment;

    private String checker;

    private Integer auditState;


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date auditTime;


    private String audit_remark;

    private Integer submitterid;

    private Integer checkerid;
}
