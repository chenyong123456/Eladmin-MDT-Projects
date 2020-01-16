package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Data
@NoArgsConstructor
public class JobDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    private Long sort;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean enabled;

    /**
     * 护士长
     */
    private String headNurse;

    /**
     * 邀请人（参加MDT的人）
     */
    private Long inviter;

    /**
     * 联系方式
     */
    private String contactPhone;

    private DeptDTO dept;

    /**
     * 创建日期
     */
    private Timestamp createTime;
    /**
     * 如果分公司存在相同部门，则显示上级部门名称
     */
    private String deptSuperiorName;



    public JobDTO(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public JobDTO(Long id, Long sort, String name, Boolean enabled, Timestamp createTime, String headNurse, Long inviter, String contactPhone, DeptDTO dept, String deptSuperiorName) {
      this.id = id;
      this.sort = sort;
      this.name = name;
      this.enabled = enabled;
      this.createTime = createTime;
      this.headNurse = headNurse;
      this.inviter = inviter;
      this.contactPhone = contactPhone;
      this.dept = dept;
      this.deptSuperiorName = deptSuperiorName;
    }
}
