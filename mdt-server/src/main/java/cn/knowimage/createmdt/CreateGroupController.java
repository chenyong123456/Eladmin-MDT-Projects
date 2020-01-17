package cn.knowimage.createmdt;


import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.pojo.instance.MDT;
import cn.knowimage.service.IllnessHistoryService;
import cn.knowimage.service.MDTService;
import cn.knowimage.service.UserService;
import cn.knowimage.utils.ClincialResult;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * 群的基本信息
 * {
 * "Owner_Account": "leckie", // 群主的 UserId（选填）
 * "Type": "Public", // 群组类型：Private/Public/ChatRoom/AVChatRoom/BChatRoom（必填）
 * "Name": "TestGroup", // 群名称（必填）
 * "Introduction": "This is group Introduction", // 群简介（选填）
 * "Notification": "This is group Notification", // 群公告（选填）
 * "FaceUrl": "http://this.is.face.url", // 群头像 URL（选填）
 * "MaxMemberCount": 500, // 最大群成员数量（选填）
 * "ApplyJoinOption": "FreeAccess"  // 申请加群处理方式（选填）
 * }
 * 作为创建群组消息
 * @author yong.Mr
 * @date 2019-12-18
 * @version 1.0
 */
@RestController
@CrossOrigin
public class CreateGroupController {

    /**
     * 注入人员表的service接口
     */
    @Autowired
    private UserService userService;
    /**
     * 注入mdt的service的接口
     */
    @Autowired
    private MDTService mdtService;
    /**
     * 注入病史service的接口
     */
    @Autowired
    private IllnessHistoryService illnessHistoryService;

    /**
     * 给两个静态变量, 记录上一次请求的数据
     */
    private static String queryNames = "";
    private static String queryConsultations = "";

    /**
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * 创建MDT的Controller ----- 给大哥的接口
     * @params jsonStr
     * @return ClincialResult
     */
    @Transactional
    @RequestMapping(value = "MDT", produces = "application/json")
    public ClincialResult submitMDT(String form, String mdtImageid) {
        System.out.println("******************************************创建MDT******************************************");
        System.out.println("大哥的图片信息序号出来了吗----->:" + mdtImageid);
        System.out.println("cscsdcs************:" + form);
        JSONObject jsonObject = JSONObject.parseObject(form);
        System.out.println("转换为JSONObject----->:" + jsonObject.toJSONString());
        CreateMDTVo createMDTVo = jsonObject.toJavaObject(CreateMDTVo.class);
        System.out.println("前台是否传入数据----->:" + createMDTVo.toString());
        System.out.println("前台传过来的数据相关病史数据----->:" + createMDTVo.getMedHistory());
        //用户userId----->创建者用户的id
        //Integer uid = (Integer) jsonObject.get("uid");
        Integer uid = createMDTVo.getUid();
        //前台传过来的数据这几给不能为空
        if (uid == 0 || StringUtils.isBlank(createMDTVo.getMdtName()) || StringUtils.isBlank(createMDTVo.getRegion()) || createMDTVo.getType().length == 0) {
            return ClincialResult.build(300, "数据传入错误!");
        }
        System.out.println(createMDTVo.toString());
        System.out.println(uid);
        ClincialResult clincialResult = mdtService.insertMDT(createMDTVo, uid);
        System.out.println("创建MDT成功返回给前端的数据----->:" + clincialResult.toString());
        //更新病列表
        ClincialResult clincialResult1 = illnessHistoryService.insertIllnessHistory(createMDTVo, (MDT) clincialResult.getData(), mdtImageid);
        System.out.println("加入病例史的数据是否成功----->:" + clincialResult1.toString());

        System.out.println("******************************************创建MDT******************************************");

        return clincialResult;
    }

    /**
     * 查询该用户涉及的MDT信息（给大哥PC端的MDT列表）-----接入相关分页和模糊搜索
     * @param uid
     * @return
     */
    @RequestMapping(value = "getMdt")
    public net.sf.json.JSONObject getMDTList(String uid, @RequestParam(required = false, defaultValue = "10") String pageList, @RequestParam(required = false, defaultValue = "1") String currentPage,
                                             @RequestParam(required = false, defaultValue = "") String queryName, @RequestParam(required = false, defaultValue = "") String queryConsultation) {
        // 前台传过来的字符, 经过了编码转换, 在这里进行编码的再次转换
        try {
            queryConsultation = URLDecoder.decode(queryConsultation,"UTF-8");
            queryName = URLDecoder.decode(queryName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("每页的数据量----->:" + pageList);
        System.out.println("页数----->:" + currentPage + "********" + queryName + "*********" + queryConsultation);
        System.out.println("前台传过来的查询病人名字的数据----->:" + queryName);
        System.out.println("前台传过来的查询MDT名字的数据----->:" + queryConsultation);
        if((!"".equals(queryName) || !"".equals(queryConsultation)) && (!queryName.equals(queryNames) || !queryConsultation.equals(queryConsultations) )){
            currentPage = "1";
        }

        this.queryNames = queryName;
        this.queryConsultations = queryConsultation;

        JSONArray result = new JSONArray();
        net.sf.json.JSONObject responseResult = new net.sf.json.JSONObject();

        if("'".equals(queryName) || "'".equals(queryConsultation) || "%".equals(queryConsultation)
                || "%".equals(queryName) || queryName.contains("'") || queryConsultation.contains("'")){
            System.out.println("请求进来了吗!");
            responseResult.put("mdtList", result);
            responseResult.put("total", 0);
            responseResult.put("page", 1);
            return responseResult;
        }
        int dataNumber = Integer.parseInt(pageList);
        int currentPageNumber = Integer.parseInt(currentPage);
        System.out.println("前台查询列表的数据----->:" + uid);
        if(NumberUtils.isNumber(uid)) {
            return mdtService.getMDTListByUser(Integer.parseInt(uid), dataNumber, currentPageNumber, queryName, queryConsultation);
        }
        return mdtService.getMDTListByUser(0, dataNumber, currentPageNumber, queryName, queryConsultation);
    }
}
