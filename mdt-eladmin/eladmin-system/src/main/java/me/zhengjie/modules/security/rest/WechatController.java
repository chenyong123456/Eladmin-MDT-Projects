package me.zhengjie.modules.security.rest;
import jdk.nashorn.internal.objects.annotations.Getter;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.utils.HttpRequestUtils;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.WechatLogin;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.WechatLoginService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




/**
 * 微信处理
 * @author lyx
 * @date 2019.12.18
 */
@RestController
@RequestMapping("api")
public class WechatController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${appid}")
  private String appid;

  @Value("${callBack}")
  private String callBack;

  @Value("${scope}")
  private String scope;

  @Value("${appsecret}")
  private String appsecret;
  @Autowired
  private UserService userService;
  @Autowired
  private WechatLoginService wechatLoginService;

  @Autowired
  private RedisService redisService;
  /**
   * 获取微信二维码
   * @param code 微信端所需的code
   * @param state 前端自定义一个uuid+时间戳，用于判断登录状态
   * @return ResponseEntity 响应实体
   * @throws Exception
   */
  @GetMapping("/callBack")
  public ResponseEntity callBack(String code, String state) throws Exception {
    System.out.println("|------------------------------我只希望能够成功一次-----------------------------------|" + state);
    logger.info("进入授权回调,code:{},state:{}", code, state);

    //1.通过code获取access_token
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    url = url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
    String tokenInfoStr = HttpRequestUtils.httpGet(url, null, null);
    JSONObject tokenInfoObject = JSONObject.fromObject(tokenInfoStr);
    System.out.println("|-------------------openId----------------------------|" + tokenInfoObject.toString());
    logger.info("tokenInfoObject:{}", tokenInfoObject);
    //拿到openId(暂时不用) String openId = tokenInfoObject.getString("openid");
    //2.拿到unionId
     String unionId = tokenInfoObject.getString("unionid");
     //3、通过access_token和openid获取用户信息
    String userInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    userInfoUrl = userInfoUrl.replace("ACCESS_TOKEN",tokenInfoObject.getString("access_token")).replace("OPENID",tokenInfoObject.getString("openid"));
    String userInfoStr =  HttpRequestUtils.httpGet(userInfoUrl,null,null);
    JSONObject jsonUserObject = JSONObject.fromObject(userInfoStr);

    WechatLogin wechatLogin = new WechatLogin();
    wechatLogin.setUnionId(unionId);
    wechatLogin.setSign(state);
    wechatLogin.setHeadImageUrl(jsonUserObject.getString("headimgurl"));
    //新增一条临时的登录状态表数据
    wechatLoginService.create(wechatLogin);
    logger.info("userInfoObject:{}",userInfoStr);
    System.out.println("微信登录用户信息==================================》"+jsonUserObject.toString());

    return new ResponseEntity(HttpStatus.OK);

  }

  /**
   * 检查登录状态
   * @param sign uuid+时间戳生成的唯一标识 用于检查用户的登录状态
   * @return JSONObject
   */
  @GetMapping("/checkLogin")
  public JSONObject checkLogin(@RequestParam(value = "sign") String sign){
      JSONObject json = new JSONObject();
      /*Thread.sleep(1000);*/
      WechatLogin weChatLogin = wechatLoginService.findOpenIdBySign(sign);
      if(weChatLogin == null){
        json = new JSONObject();
        System.out.println("该用户没有登录");
        //return new ResponseEntity(HttpStatus.CONFLICT);
        json.put("status",401);
        return json;
      }else{
        System.out.println("用户已登录");
        System.out.println("weChatLogin.getUnionId()"+weChatLogin.getUnionId());
        //根据unionId查询对应的用户
        User user = userService.selectWx(weChatLogin.getUnionId());
        if(user == null){
          System.out.println("该微信没有绑定系统用户，请先绑定");
          json.put("status",402);
          json.put("unionId",weChatLogin.getUnionId());
          json.put("headImageUrl",weChatLogin.getHeadImageUrl());
        }else{
          //如果用户头像发生变化
          if(!weChatLogin.getHeadImageUrl().equals(user.getWxAvatar())) {
            //修改用户头像为登录时选择的用户昵称
            userService.updateWxByPhone(weChatLogin.getUnionId(), weChatLogin.getHeadImageUrl(), user.getPhone());
            //修改完应该清理一次redis缓存
            String key = "user::loadUserByUsername:" + user.getUsername();
            redisService.delete(key);
          }
          json.put("username",user.getUsername());
          json.put("password","password");
          json.put("uuid","wxlogin");
          json.put("code","don't need code");
        }
        return  json;
      }
}

  /**
   * 绑定微信
   * @param phone 电话号码
   * @return
   */
  @GetMapping("/bindWechat")
  public JSONObject bindWechat(@RequestParam(value = "phone") String phone,String headImageUrl,@RequestParam(value = "unionId") String unionId){
    JSONObject json = new JSONObject();
    if(userService.selectUserByPhone(phone)==null){
       //该手机号未绑定该微信号
       json.put("status",404);
    }else if(userService.selectUserByPhone(phone).getWx()!=null){
      //该手机号已被绑定
      json.put("status",402);
    }else{
      //绑定成功
      userService.updateWxByPhone(unionId,headImageUrl,phone);
      json.put("nickname",userService.selectUserByPhone(phone).getUsername());
      json.put("uid",userService.selectUserByPhone(phone).getId());
      json.put("status",200);
    }
    return json;
  }
}
