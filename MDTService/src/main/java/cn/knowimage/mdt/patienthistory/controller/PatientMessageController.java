package cn.knowimage.mdt.patienthistory.controller;


import cn.knowimage.utils.ClincialResult;
import cn.knowimage.wechat.mdtcorrelation.service.impl.IfAcceptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主要处理病史信息的相关接口
 * @author yong.Mr
 * @date 20201-10
 * @version 1.0.0
 */
@Transactional
@CrossOrigin
@RestController
@RequestMapping("/pamsg")
public class PatientMessageController {

    /**
     * 注入IfAcceptServiceImpl
     */
    @Autowired
    private IfAcceptServiceImpl ifAcceptService;

    /**
     * 获取该病人信息的相关的病史Controller -----> 微信小程序
     * @param mid MDT的ID
     * @param uid 登录人的ID
     * @return ClincialResult
     */
    @RequestMapping(value = "/patHisMsg")
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
