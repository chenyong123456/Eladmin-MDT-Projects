package cn.knowimage.mdt.controller;

;
import cn.knowimage.utils.ClincialResult;
import cn.knowimage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 人员注册,需要导入到腾讯云中进行注册
 * @author yong.Mr
 * @date 2019-01-02
 * @version 1.0
 */
@Slf4j
@RestController
@CrossOrigin
@Transactional
public class ImportMember {

    private static final String importMemberUrl = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid=1400288517&identifier=user0&usersig=eJw1zMEKgkAUheF3udtCrjZ3FKFNBRWZm4LW1oxxi3Sa0RCjd0-GWp7vwP*GY3YIXtpCClGAMPWbla4aLtlz67T9H07dC2NYQRoKxChJKIzHR3eGrR6ciCJEHLXhh7eYZiQFyl*Fr0OXdpebxEy34rlfJZucz32dq8W6nFS15I5UuF32p6KQws3h8wUR1jEN&random=99999985&contenttype=json";

    /**
     * 单个用户的导入腾讯云----->梁注册的接口
     * @param username 需要导入到腾讯云的用户名
     * @return ClincialResult
     */
    @RequestMapping("/importMember")
    public static ClincialResult importSingleMember(String username){
        System.out.println("**************************单个用户的导入腾讯云****************************");
        JSONObject importData = new JSONObject();
        importData.put("Identifier", username);
        String flag = HttpClientUtil.doPostJson(importMemberUrl, importData.toString());
        JSONObject responseResult = JSONObject.fromObject(flag);
       if("OK".equals(responseResult.getString("ActionStatus"))){
           return ClincialResult.build(200,"成功!", null);
       }
        return ClincialResult.build(500,"失败!", null);
    }
}
