package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author lyx
 * @date 2019/12/21
 */
@Entity
@Getter
@Setter
@Table(name="WECHAT_LOGIN")
public class WechatLogin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull(groups =Update.class)
  private Long id;

  private String unionId;
  @Column(name = "head_image_url")
  private String headImageUrl;

  private String sign;

  @Override
  public String toString() {
    return "WechatLogin{" +
      "id=" + id +
      ", unionId='" + unionId + '\'' +
      ", headImageUrl='" + headImageUrl + '\'' +
      ", sign='" + sign + '\'' +
      '}';
  }

  public @interface Update {}
}
