package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class AuditInfoQueryCriteria implements Serializable {
    @Query
    private Integer pathwayId;

    @Query(type = Query.Type.INNER_LIKE)
    private String pathwayName;
    @Query(type = Query.Type.INNER_LIKE)
    private String submitter;

    @Query(type = Query.Type.INNER_LIKE)
    private String dataUploadMoment;
    @Query(type = Query.Type.INNER_LIKE)
    private String checker;
    @Query
    private Integer auditState;

    @Query(type = Query.Type.INNER_LIKE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @Query(type = Query.Type.INNER_LIKE)
    private String audit_remark;
    @Query
    private Integer submitterid;
    @Query
    private Integer checkerid;
}
