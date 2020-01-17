package cn.knowimage.ims.group;


import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.ims.vo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 生成创建群组的相关的实体生成类
 * @author yong.Mr
 * @data 2019-11-20
 * @version 1.0.0
 */
public class GroupBody {
    /**
     *{
     *   "Name": "TestGroup", // 群名称（必填）
     *   "Type": "Public", // 群组类型：Private/Public/ChatRoom(不支持AVChatRoom和BChatRoom)（必填）
     *   "MemberList": [ // 初始群成员列表，最多500个（选填）
     *        {
     *           "Member_Account": "bob", // 成员（必填）
     *           "Role": "Admin" // 赋予该成员的身份，目前备选项只有 Admin（选填） 成员身份
     *        },
     *        {
     *           "Member_Account": "peter"
     *        }
     *    ]
     * }
     * @param createMDTVo 前台传过来的数据
     * @return JSONObject
     */
    public static JSONObject getGroupBody(CreateMDTVo createMDTVo, List<User> user, String batchImport, String whetherImport){
        //创群对象
        JSONObject group = new JSONObject();
        //拼接相应的值
        //group.put("Owner_Account", "user0");
        group.put("Name", "45");
        group.put("Type", "Public");
        //创建初始化群成员--->数组类型
        //在这里把没有导入腾讯云中的成员导入到腾讯云中
        boolean flag = CheckImportMember.checkImport(user, batchImport, whetherImport);
        if(flag){
            System.out.println("所有成员导入成功");
        }else{
            System.out.println("所有成员带哦如失败!");
        }

        //每一对象代表一个成员
        JSONArray member = new JSONArray();
        //请去查询数据库中要加入的群成员--->这个数组可能有两种格式----->有多少个初始成员就放入一个对象中,代表一个成员
        JSONObject memberOne = null;
        for (int i = 0; i< user.size(); i++){
            memberOne = new JSONObject();
            //从该数据库中查询出相应的数据----->根据数据
            memberOne.put("Member_Account", user.get(i).getUsername());
            member.add(memberOne);
        }

        //加入初始化的群成员
        group.put("MemberList", member);

        System.out.println("初始化的Group的请求body:" + group.toString());
        return group;
    }

    private final static String allForbidSendMsg = "https://console.tim.qq.com/v4/group_open_http_svc/forbid_send_msg?sdkappid=1400307615&identifier=IMSlh&usersig=eJwtzF0LgjAYhuH-stNCXldrKXjUgQgzghnoYbGpb2aMzT4w*u-J9PC5Hri-pBAyeGlLYkIDIGu-UenHgDV6znJ5b5fDqe5iDCoSh1uADfBdyOZHfwxaPTljjALArAP23nhEKeN7ulSwmbrtkeapvZ4EVM-6BiWTIIrejertwmZMjc6qcljZ6HzoEvL7AwLaMXg_&random=99999998&contenttype=json";

    /**
     * 进行全员禁言
     * {
     *   "GroupId": "@TGS#2C5SZEAEF",
     *   "Members_Account": [ // 最多支持500个
     *       "peter",
     *       "leckie"
     *   ],
     *   "ShutUpTime": 60 // 禁言时间，单位为秒
     * }
     * @return JSONObject
     */
    public static Boolean forbidSendMsg(List<User> users, String groupId){
        System.out.println("*****************************对群组中中的所有的成员进行禁言********************************");
        JSONObject jsonBody = new JSONObject();
        JSONArray memberArray = new JSONArray();
        jsonBody.put("GroupId", groupId);
        jsonBody.put("ShutUpTime",1000000); // 设置禁言时间
        for (User user : users) {
            memberArray.add(user.getUsername());
        }
        System.out.println("全员禁言的所有数据----->:" + jsonBody.toString());
        String forbidSendMsgResult = HttpClientUtil.doPostJson(allForbidSendMsg, jsonBody.toString());
        JSONObject responseResult = JSONObject.fromObject(forbidSendMsgResult);
        if("OK".equals(responseResult.getString("ActionStatus"))){
            return true;
        }
        return false;
    }


}
