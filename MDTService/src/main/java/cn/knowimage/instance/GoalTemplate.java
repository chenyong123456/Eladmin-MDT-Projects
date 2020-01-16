package cn.knowimage.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 对表GOAL_TEMPLATE映射的实体类
 * @author yong.Mr
 * @date 2020-1-9
 * @version 1.0.0
 */
@Data
@Table(name = "GOAL_TEMPLATE")
public class GoalTemplate {

    // 主键id
    @Id
    @GeneratedValue(generator = "JDBC")  // 执行insert操作后将主键回写到实体类中
    @Column(name = "id")
    private Integer id;

    // @JsonProperty("value")改变返回前台的json的key键名
    @JsonProperty("value")
    @Column(name = "mdt_goal")
    private String mdtGoal;

    // 创建时间
    @JsonIgnore  // 返回给页面时忽略此字段 , 需要引入 jackson-databind 依赖 ,序列化和反序列化都受影响。
    @Column(name = "create_time")
    private Date createTime;

    // 修改时间
    @JsonIgnore
    @Column(name = "modify_time")
    private Date modifyTime;

    // 预留字段
    @Transient  // 标记非数据库表的字段
    @JsonIgnore
    private String username;
}
