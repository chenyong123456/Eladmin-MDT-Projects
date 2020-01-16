package me.zhengjie.modules.security.rest;

import com.alibaba.fastjson.JSON;
import me.zhengjie.modules.security.utils.AES;
import me.zhengjie.modules.security.utils.HttpRequestUtils;
import me.zhengjie.modules.security.utils.WxPKCS7Encoder;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信小程序业务
 * @author  lyx
 * @date    2019/12/24
 */
@RestController
@RequestMapping("api")
public class WeChatAppletController {
  private String appId = "wx75840bf24d0379fd";
  private String secret = "c194a5307703f6ef6626bd4cc405bf41";
  @Autowired
  private UserService userService;

  /**
   * 根据code拿到session_key
   * @param code
   * @return
   * @throws Exception
   */
  @GetMapping("getAppletOpenId")
  public JSONObject  getWechatApplet(String code) throws Exception{
    System.out.println("code============================>"+code);
    JSONObject jsonObject = new JSONObject();
    String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
    String tokenInfoStr = HttpRequestUtils.httpGet(url,null,null);
    JSONObject tokenJson = JSONObject.fromObject(tokenInfoStr);
    System.out.println("|------------------小程序的json-------------------------------|"+tokenJson.toString());
    String openId = tokenJson.getString("openid");
    String session_key = tokenJson.getString("session_key");
    jsonObject.put("session_key",session_key);
    jsonObject.put("openid",openId);
    return jsonObject;
  }


  /**
   * 获取小程序的unionId
   * @param code
   * @param sessionKey
   * @param encryptedData
   * @param iv
   * @return
   * @throws Exception
   */
  @GetMapping("getWechatAppletUnionId")
  public JSONObject  getWechatAppletUnionId(String code,String sessionKey,String encryptedData,String iv) throws Exception {
    System.out.println("code============================>" + code);
    System.out.println("sessionKey======================>" + sessionKey);
    System.out.println("encryptedData============================>" + encryptedData);
    System.out.println("iv======================>" + iv);
    JSONObject jsonObject = new JSONObject();
    String data = encryptedData.replaceAll(" ", "+");
    String ivstr = iv.replaceAll(" ", "+");
    AES aes = new AES();
    byte[] resultByte = aes.decrypt(Base64.decodeBase64(data), Base64.decodeBase64(sessionKey),
      Base64.decodeBase64(ivstr));
    if (null != resultByte && resultByte.length > 0) {
      String userInfo = new String(WxPKCS7Encoder.decode(resultByte));
      JSONObject wxinfo = JSONObject.fromObject(userInfo);
      System.out.println("unionId=====================================" + wxinfo.getString("unionId"));
      String unionId =  wxinfo.getString("unionId");
      jsonObject.put("unionId",unionId);
      //根据微信号唯一标识查询user
      User user = userService.selectWx(unionId);
      //该微信号未被绑定
      if(user==null){
        jsonObject.put("status",404);
      }else{
        jsonObject.put("nickname",user.getUsername());
        jsonObject.put("uid",user.getId());
        System.out.println("user.getUsername()==============>"+user.getUsername());
        jsonObject.put("status",200);
      }
    }
    return jsonObject;

  }

}
