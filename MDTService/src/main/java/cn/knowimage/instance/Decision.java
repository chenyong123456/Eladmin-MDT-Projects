package cn.knowimage.instance;

import cn.knowimage.ims.vo.User;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "decision")
public class Decision {
    @Id
    private Integer id;

    private Integer user_id;

    @Transient
    private User user;

    private Integer mdt_id;

    @Transient
    private MDT mdt;

    //每个用户的主要观点内容
    private String standPoint;
    //总决策内容
    private String decision_content;
}
