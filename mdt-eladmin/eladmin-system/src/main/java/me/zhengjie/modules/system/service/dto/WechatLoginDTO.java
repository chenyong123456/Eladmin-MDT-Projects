package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * @author Liang Yuxuan
 * @date 2019-12-20
 */
@Data
public class WechatLoginDTO implements Serializable {
  private Long id;

  private String unionId;

  private String headImageUrl;

  private String sign;

}
