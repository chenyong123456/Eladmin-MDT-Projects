package cn.knowimage.wechat.controller;


import cn.knowimage.wechat.mdtcorrelation.service.IfAcceptService;
import cn.knowimage.utils.ClincialResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录用户是否接收该MDT会议
 * @author yong.Mr
 * @version 1.0
 * @data 2019-12-03
 */
@Slf4j
@RestController
@RequestMapping("/if")
@CrossOrigin
public class IfAcceptController {

    /**
     * 注入相关的service层
     */
    @Autowired
    private IfAcceptService ifAcceptService;

    /**
     * 供前台接收该场MDT会议 -----> 同意该场MDT的相关信息
     * @return String
     */
    @RequestMapping("/accept")
    public ClincialResult ifAcceptMdt(@RequestParam(required = false) Integer uid, @RequestParam(required = false) Integer mid,
                                      @RequestParam(required = false) String groupId) {
        System.out.println("******************用户接收该场MDT-begin********************************");
        System.out.println("前台是否传入数据----->:" + uid + "-----" + mid);
        System.out.println("前台是否传入groupId数据----->:" + groupId);
        //调用Service层的业务逻辑
        ClincialResult clincialResult = ifAcceptService.agreeMdt(uid, mid, groupId);
        System.out.println("***********************用户接收该场MDT-end***********************");
        System.out.println("csdcs----->:" + clincialResult.toString());
        return clincialResult;
    }

    /**
     * 推荐其他人的业务逻辑 -----> 查询出同一科室的其他人相关信息
     * @return ClincialResult
     */
    @RequestMapping("/otherPeople")
    public ClincialResult recommendOther(@RequestParam(required = false) Integer uid, @RequestParam(required = false) Integer mid,
                                         @RequestParam(required = false) String groupId) {
        System.out.println("uid----->:" + uid);
        System.out.println("mid----->:" + mid);
        System.out.println("前台是否传入groupId数据----->:" + groupId);
        //在这里写相应的业务逻辑----->将该用该场的MDT状态变为1,已拒绝
        ClincialResult clincialResult = ifAcceptService.recommendOther(uid, mid, groupId);
        System.out.println("查询出来的人员数据----->:" + clincialResult.toString());
        return clincialResult;
    }

    /**
     * 推荐其他refuse参与mdt的会议
     * @param uid 登录人的ID
     * @param mid mdt的ID
     * @param groupId
     * @return ClincialResult
     */
    @RequestMapping("/therPeopleRefuse")
    public ClincialResult recommendOtherRefuse(@RequestParam(required = false) Integer uid, @RequestParam(required = false) Integer mid,
                                               @RequestParam(required = false) String groupId) {
        System.out.println("***********************************推荐人拒绝**********************************");
        System.out.println("被推荐的人拒绝登陆人的Id是否传入----->:" + uid);
        System.out.println("被推荐的人拒绝MDT的Id是否传入----->:" + mid);
        System.out.println("*****************************************************************************");
        ClincialResult clincialResult = ifAcceptService.recommendPeopleRefuse(uid, mid, groupId);
        System.out.println("***********************************推荐人拒绝**********************************");
        return clincialResult;
    }

    /**
     * 点击推荐其他人成功的业务逻辑 -----> 推荐其他人点击了相关推荐的人
     * @param uid 为登录人id
     * @return ClincialResult
     */
    @RequestMapping("/otherPeopleAgree")
    public ClincialResult recommendOtherAgree(@RequestParam(required = false) Integer uid, @RequestParam(required = false) Integer mid,
                                              @RequestParam(required = false) String groupId,@RequestParam(required = false) Integer recommendPeopleId) {
        System.out.println("***********************************推荐人同意**********************************");
        System.out.println("点击了相关推荐的人recommendPeopleId_otherPeopleAgreee----->:" + recommendPeopleId);
        System.out.println("uid_agree----->:" + uid);
        System.out.println("mid_agree----->:" + mid);
        System.out.println("前台是否传入groupId数据----->:" + groupId);
        System.out.println("***********************************推荐人同意**********************************");
        //在这里写相应的业务逻辑----->将该用该场的MDT状态变为1,已拒绝
        ClincialResult clincialResult = ifAcceptService.recommendOtherAgree(uid, mid, groupId,recommendPeopleId);
        System.out.println("推荐其他人成功返回的数据为----->:" + clincialResult.toString());
        return clincialResult;
    }
    /**
     * 获取该病人信息的相关的病史Controller -----> 微信小程序
     * @param mid MDT的ID
     * @param uid 登录人的ID
     * @return ClincialResult
     */
    @RequestMapping(value = "/bodyMsg")
    public ClincialResult getPeopleMsg(@RequestParam Integer mid, @RequestParam Integer uid) {
        System.out.println("**************************查询病人的基本信息**************************");
        System.out.println("微信小程序的mdtId是否传入----->:" + mid);
        System.out.println("微信小程序的用户uid是否掺入----->:" + uid);
        // 记住一切函数传参都是传址
        ClincialResult peopleMsg = ifAcceptService.getPeopleMsg(mid, uid);
        if (peopleMsg.getStatus() == 200) {
            // 代表业务获取成功
            return peopleMsg;
        }
        System.out.println("**************************查询病人的基本信息**************************");
        return peopleMsg;

    }

}
