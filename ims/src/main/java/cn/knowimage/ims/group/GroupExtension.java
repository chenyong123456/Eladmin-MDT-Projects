package cn.knowimage.ims.group;


import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.ims.vo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 主要对GroupBody进行扩展(都是腾讯云的相关接口进行调用)
 * @author yong.Mr
 * @data 2019-11-22
 * @version 1.0
 */
public class GroupExtension extends GroupBody {
    /**
     * 请求腾讯云删除群组成员中的成员的请求URL
     */
    public static final String deleteGroupMember = "https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member?sdkappid=1400288517&identifier=user&usersig=eJwtzEELgjAcBfDvsqsh29zyn9ClbuEpjcSbtTn*lWtMEyn67sns9Hi-B*9DyryIR*1JRnhMySp0VNoO2GLgVz-H4r26N86hIhkTlHIAydJl0ZNDr2eXUnJK6aIDdsFSAQmwjfi-oJlvH2ZvzbTzXVWfoxtgOjYcXNJGub0wfB*hWp*uRVk3h*eWfH8EvjFY&random=99999998&contenttype=json";
    /**
     * 请求腾讯云取消禁言群组成员中的成员的请求URL
     */
    private final static String allForbidSendMsg = "https://console.tim.qq.com/v4/group_open_http_svc/forbid_send_msg?sdkappid=1400288517&identifier=user0&usersig=eJw1zMEKgkAUheF3udtCrjZ3FKFNBRWZm4LW1oxxi3Sa0RCjd0-GWp7vwP*GY3YIXtpCClGAMPWbla4aLtlz67T9H07dC2NYQRoKxChJKIzHR3eGrR6ciCJEHLXhh7eYZiQFyl*Fr0OXdpebxEy34rlfJZucz32dq8W6nFS15I5UuF32p6KQws3h8wUR1jEN&random=99999985&contenttype=json";
    /**
     * 请求腾讯云的单给导入账号信息URL
     */
    private final static String accountImport = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid=1400288517&identifier=user0&usersig=eJw1zMEKgkAUheF3udtCrjZ3FKFNBRWZm4LW1oxxi3Sa0RCjd0-GWp7vwP*GY3YIXtpCClGAMPWbla4aLtlz67T9H07dC2NYQRoKxChJKIzHR3eGrR6ciCJEHLXhh7eYZiQFyl*Fr0OXdpebxEy34rlfJZucz32dq8W6nFS15I5UuF32p6KQws3h8wUR1jEN&random=99999985&contenttype=json";
    /**
     * 解除该成员的禁言
     * @param users 成员信息
     * @param groupId 群组ID
     * @return Boolean
     */
    public static Boolean forbidSendMsg(User users, String groupId) {
        System.out.println("*****************************对群组中中的单给成员进行解除禁言********************************");
        JSONObject jsonBody = new JSONObject();
        JSONArray memberArray = new JSONArray();
        jsonBody.put("GroupId", groupId);
        jsonBody.put("ShutUpTime",0); // 设置禁言时间, 表示取消禁言
        memberArray.add(users.getUsername());
        jsonBody.put("Members_Account",memberArray);
        System.out.println("解除该成员的禁言----->:" + jsonBody.toString());
        String forbidSendMsgResult = HttpClientUtil.doPostJson(allForbidSendMsg, jsonBody.toString());
        JSONObject responseResult = JSONObject.fromObject(forbidSendMsgResult);
        if("OK".equals(responseResult.getString("ActionStatus"))){
            return true;
        }
        return false;
    }

    /**
     * {
     *    "Identifier":"test",
     *    "Nick":"test",
     *    "FaceUrl":"http://www.qq.com"
     * }
     * 修改成员的相关信息(图像)
     * @param users 成员信息
     * @param url 群组ID
     * @return Boolean
     */
    public static Boolean importSingleMember(User users, String url) {
        System.out.println("*****************************对群组中中的单给成员修改成员的相关信息(图像)********************************");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("Identifier", users.getUsername());
        jsonBody.put("Nick", users.getUsername());
        jsonBody.put("FaceUrl", url);
        System.out.println("修改成员的相关信息(图像)----->:" + jsonBody.toString());
        String forbidSendMsgResult = HttpClientUtil.doPostJson(accountImport, jsonBody.toString());
        JSONObject responseResult = JSONObject.fromObject(forbidSendMsgResult);
        System.out.println("*****************************对群组中中的单给成员修改成员的相关信息(图像)********************************");
        if("OK".equals(responseResult.getString("ActionStatus"))){
            return true;
        }
        return false;
    }

    /**
     * {
     *   "GroupId": "@TGS#2J4SZEAEL", // 要操作的群组（必填）
     *   "MemberToDel_Account": [ // 要删除的群成员列表，最多500个
     *       "tommy",
     *       "jared"
     *   ]
     * }
     * 删除指定群组成员, 拼接成相应的json格式
     * @return JSONObject
     */
    public static Boolean deleteGroupMember(List<User> user, String groupId) {
        System.out.println("腾讯云的groupId是否进来了----->:" + groupId);
        //获取基础json格式
        JSONObject base = new JSONObject();
        JSONArray memberArray = new JSONArray();
        for (User usr:user) {
            memberArray.add(usr.getUsername());
        }
        base.put("GroupId", groupId);
        base.put("MemberToDel_Account",memberArray);
        System.out.println("拼接完腾讯云的json字符串! ----->: " + base.toString());
        // 调用腾讯云的相关接口
        String result = HttpClientUtil.doPostJson(deleteGroupMember, base.toString());
        System.out.println("**********************腾讯云相应的结果-begin*****************************");
        System.out.println("腾讯云返回的相应----->:" + result);
        JSONObject responseResult = JSONObject.fromObject(result);
        System.out.println("**********************腾讯云相应的结果-end********************************");
        if("OK".equals(responseResult.getString("ActionStatus"))){
            return true;
        }
        return false;
    }

}
