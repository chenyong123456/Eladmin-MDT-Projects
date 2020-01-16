//package me.zhengjie.modules.security.service;
//
//import cn.hutool.core.date.DateUtil;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.api.WxMpService;
////import me.zhengjie.modules.security.utils.MD5Utils;
////import me.zhengjie.modules.security.utils.MD5Utils;
//import me.zhengjie.utils.MD5Util;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import sun.security.provider.MD5;
//
//import java.util.Date;
//
//
//@Service
//@Slf4j
//public class WeChatServiceImpl implements WeChatService {
//
//  @Autowired
//  private WxMpService wxMpService;
//
//  @Value("${wx.open.config.redirectUrl}")
//  private String wxRedirectUrl;
//
//  @Value("${wx.open.config.csrfKey}")
//  private String CSRF_KEY;
//
//  @Override
//  public String getQRCodeUrl() {
//    // 生成 state 参数，用于防止 csrf
//    String date = DateUtil.format(new Date(), "yyyyMMdd");
//    //String state = MD
//    //return wxMpService.buildQrConnectUrl(wxRedirectUrl, Constant.WeChatLogin.SCOPE, state);
//    return "";
//  }
//
//  // 省略
//}
