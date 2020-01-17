package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 投票设置类型
 * @author Liang Yuxuan
 * @date 2020/01/04
 */
@Table(name ="VOTING_SETTING_TYPE")
@Data
public class VotingSettingType {
  /**
   * ID
   */
  @Id
  @GeneratedValue(generator = "JDBC")
  private Long id;
  /**
   * 名称
   */
  @Column(name = "name",nullable = false)
  @NotBlank
  private String name;
  /**
   * 投票机制
   */
  @Column(name = "voting_mechanism")
  private Integer votingMechanism;
}
