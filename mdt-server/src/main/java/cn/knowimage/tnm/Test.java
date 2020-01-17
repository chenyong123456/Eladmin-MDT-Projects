package cn.knowimage.tnm;

import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.pojo.instance.Decision;
import cn.knowimage.pojo.instance.TreeNode;
import cn.knowimage.pojo.vo.TNM;
import cn.knowimage.service.DTreeService;
import cn.knowimage.service.DecisionService;
import cn.knowimage.service.TNMService;
import cn.knowimage.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 群的基本信息
 * {
 *   "Owner_Account": "leckie", // 群主的 UserId（选填）
 *   "Type": "Public", // 群组类型：Private/Public/ChatRoom/AVChatRoom/BChatRoom（必填）
 *   "Name": "TestGroup", // 群名称（必填）
 *   "Introduction": "This is group Introduction", // 群简介（选填）
 *   "Notification": "This is group Notification", // 群公告（选填）
 *   "FaceUrl": "http://this.is.face.url", // 群头像 URL（选填）
 *   "MaxMemberCount": 500, // 最大群成员数量（选填）
 *   "ApplyJoinOption": "FreeAccess"  // 申请加群处理方式（选填）
 * }
 * 作为创建群组消息
 */
@RestController
@CrossOrigin
public class Test {

    @Value("BACKGROUND")
    private String a;

    @Autowired
    private UserService userService;



    @Autowired
    private TNMService tnmService;

    @Autowired
    private DTreeService dTreeService;

    @Autowired
    private DecisionService decisionService;

    /**
     * 测试腾讯云的接口
     * @return 返回相应的消息
     */
    @RequestMapping("/send")
    public static String getSendMsg(){
        String result = HttpClientUtil.doPostJson("https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?sdkappid=1400288517&identifier=user&usersig=eJyrVgrxCdYrSy1SslIy0jNQ0gHzM1NS80oy0zLBwqXFQAoiXpySnVhQkJmiZGVoYmBgZGFhamgOkUmtKMgsSgWKm5qaGhkYGEBESzJzwWLmJoamZmamUNHizHSgsU75BcGhUZHmPiZZrgY53plZ2oWBfq4BGRYWvomZpmHG6RWm3hapOb5Vzo62SrUA6qMwkA__&random=99999955&contenttype=json",
                "{\n" +
                        "  \"GroupId\": \"@TGS#2DMQRJCGI\",\n" +
                        "  \"From_Account\": \"user0\", \n" +
                        "  \"Random\": 8912345,\n" +
                        "  \"MsgBody\": [ \n" +
                        "      {\n" +
                        "          \"MsgType\": \"TIMTextElem\", \n" +
                        "          \"MsgContent\": {\n" +
                        "              \"Text\": \"red scsc\"\n" +
                        "          }\n" +
                        "      },\n" +
                        "      {\n" +
                        "          \"MsgType\": \"TIMFaceElem\", \n" +
                        "          \"MsgContent\": {\n" +
                        "              \"Index\": 6,\n" +
                        "              \"Data\": \"abc\\u0000\\u0001\"\n" +
                        "          }\n" +
                        "      }\n" +
                        "  ]\n" +
                        "}");
        return result;
    }
    @RequestMapping("/test")
    public  String getA() {
        System.out.println("进入了测试的聊天的接口!");
        System.out.println("a --- > " + a);
        return "-->:";
    }

    /**
     * 主要测试该回调URL,腾讯云的接口
     * https://www.example.com?SdkAppid=$SDKAppID&CallbackCommand=$CallbackCommand&contenttype=json&ClientIP=$ClientIP&OptPlatform=$OptPlatform
     * 主方法
     * @param
     */

    @RequestMapping("login")
    public String login(String userId){
        String userSig = userService.login(userId,null);
        System.out.println("登陆请求----->:" + userSig);
        return userSig;
    }

    @GetMapping("TNM")
    public List<TNM> findByTNM(int id){
        return tnmService.findAllByMDT(id);
    }

    @PostMapping(value = "TNM",produces = "application/json")
    public List<TNM> submitTNM(@RequestBody String jsonStr){
        System.out.println(jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        Integer mid = (Integer) jsonObject.get("mid");
        Integer uid = (Integer) jsonObject.get("uid");
        String disease = jsonObject.getString("disease");
        String key = jsonObject.getString("tnm_key");
        return tnmService.insertByTNM(key,mid,uid,disease);
    }


    /**
     * 给果果的TNM返回数据 和上面的TNM 接口 效果一样
     * @param mid
     * @param uid
     * @param disease
     * @param tnm_key
     * @return
     */
    @PostMapping("TNMForm")
    public List<TNM> submitTNM(int mid, int uid, String disease, String tnm_key){
        System.out.println(mid);
        System.out.println(uid);
        System.out.println(disease);
        System.out.println(tnm_key);
        return tnmService.insertByTNM(tnm_key,mid,uid,disease);
    }

    @PostMapping(value = "tree",produces = "application/json")
    public String getTree(@RequestBody String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        TNM tnm = jsonObject.toJavaObject(TNM.class);
        TreeNode node = dTreeService.getTreeByTNM(tnm);
        return JSON.toJSONString(node);
    }

    @GetMapping(value = "tree")
    public List<TreeNode> getChildTree(int id){
        List<TreeNode> treeNodes = dTreeService.getTreeByHeadId(id);

        return treeNodes;
    }

    @PostMapping("headTree")
    public TreeNode getHeadNode(@RequestBody String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        TNM tnm = jsonObject.toJavaObject(TNM.class);
        TreeNode node = dTreeService.getHeadNode(tnm);
        return node;
    }

    @GetMapping("sameTree")
    public List<TreeNode> getSameTree(int id){
        TreeNode node = dTreeService.getNodeById(id);
        List<TreeNode> treeNodes = dTreeService.getTreeByHeadId(node.getHead_id());
        treeNodes.forEach(treeNode -> {
            if(node.getId() == treeNode.getId()) treeNode.setCheck(true);
            else treeNode.setCheck(false);
        });
        return treeNodes;
    }

    @PostMapping(value = "decision",produces = "application/json")
    public String submitDecision(@RequestBody Decision decision){
        decisionService.insert(decision);
        return "true";
    }

    @GetMapping("decision")
    public List<Decision> getDecisions(int mid){
        List<Decision> decisions = decisionService.getAllByMDT(mid);
        return decisions;
    }

}
