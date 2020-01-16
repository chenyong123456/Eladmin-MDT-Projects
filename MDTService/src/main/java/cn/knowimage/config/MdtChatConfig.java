package cn.knowimage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 功能:对redis的相关配置在这class中
 * @author yong.Mr
 * @version 1.0.0
 */
@Configuration
@PropertySource("classpath:otherness/chat.properties")// 用来指定配置文件的位置
public class MdtChatConfig {


}
