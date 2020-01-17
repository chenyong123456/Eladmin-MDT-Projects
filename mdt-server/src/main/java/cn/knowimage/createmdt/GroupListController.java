package cn.knowimage.createmdt;


import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.service.IfAcceptService;
import cn.knowimage.service.MDTService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 群的基本信息
 * @author yong.Mr
 * @version 1.0
 * @data 2019-12-03
 */
@Slf4j
@RestController
@CrossOrigin
public class GroupListController {

    @Value("${checkGroupMember}")
    private String checkGrMember;
    /**
     * 导入MDT的service
     */
    @Autowired
    private MDTService mdtServicer;

    /**
     * 获取用户对应MDT的列表----->微信小程序的Controller  GXE为大哥的请求
     * @return 该人员对应的MDT的列表
     * @params uid 该人员对应的id
     */
    @RequestMapping(value = "getMdtList")
    public JSONArray getMDTList(int uid, @RequestParam(value = "url",required = false,defaultValue = "") String url,
                                @RequestParam(value = "pc",required = false,defaultValue = "false") boolean pc) {
        System.out.println("***************************************微信小程序获取MDT的列表***************************************");
        Integer k = Integer.valueOf(10);
        System.out.println("调用MdtTnmMapper中的static方法----->:" + IfAcceptService.getInt());
        System.out.println("请求数据列表, 进来了吗----->:" + uid);
        System.out.println("绑定的微信图像----->:" + url);
        System.out.println("看否是大哥请求----->:" + pc);
        JSONArray mdtList = null;
        if(pc){
            // 大哥的请求
            mdtList = mdtServicer.getMdtParticition(uid);
        }else {
            // 小从的请求
            mdtList  = mdtServicer.getMDTList(uid, url);
        }
        return mdtList;
    }

    /**
     * {
     * "GroupId": "@TGS#2C5SZEAEF",
     * "User_Account": [ // 最多支持500个
     * "leckie",
     * "peter",
     * "wesley"
     * ]
     * }
     * @param member
     * @return
     */
    @RequestMapping(value = "checkMember", method = RequestMethod.GET)
    public JSONObject checkGroupMember(@RequestParam("member") String member, String groupId) {
        JSONObject requestBody = new JSONObject();
        JSONArray memberNum = new JSONArray();
        System.out.println("groupId是否进来了----->:" + groupId);
        requestBody.put("GroupId", groupId);
        memberNum.add(member);
        requestBody.put("User_Account", memberNum);
        System.out.println("拼接好了的请求体相关的json格式的字符串----->:" + requestBody.toString());
        String response = HttpClientUtil.doPostJson(checkGrMember, requestBody.toString());
        System.out.println("返回的数据类型----->:" + response);
        JSONObject result = new JSONObject();
        // mdtServicer
        if ("NotMember".equals(JSONObject.fromObject(response).getJSONArray("UserIdList").getJSONObject(0).getString("Role"))) {
            result.put("flag", false);
            System.out.println("是否进入了----->:" + result.toString());
            return result;
        }
        result.put("flag", true);
        System.out.println("该成员是群里的成员----->:" + result.toString());
        return result;

    }



}
