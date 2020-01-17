package cn.knowimage.Treatment;


import cn.knowimage.exception.NullException;
import cn.knowimage.service.impl.RemarkNameServiceImpl;
import cn.knowimage.service.impl.SubmitTreatmentServiceImpl;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/treatment")
public class WechatTreatmentContoller {
    @Autowired
    private SubmitTreatmentServiceImpl submitTreatmentService;
    @Autowired
    private RemarkNameServiceImpl remarkNameService;

    @RequestMapping("/submit")
    public ClincialResult submittreatment(Integer mdtId, Integer creator) {
        if (mdtId == null || creator == null) {
            throw new NullException("传入了空数据!!!");
        } else {
            System.out.println("提出治疗方案传过来的mdtId-------------->" + mdtId);
            ClincialResult submit = submitTreatmentService.submitTreament(mdtId, creator);
            return submit;
        }
    }

    @RequestMapping("/acceptInfo")
    public @ResponseBody
    ClincialResult acceptInfo(Integer mdtId, Integer creator, String treatmentId, String comment) {
        if (mdtId == null) {
            throw new NullException("请传入mdtId!!!");
        } else {
            System.out.println("前台传过来的数据----------->" + mdtId + ",创建者---》" + creator + ",治疗方法的id---》" + treatmentId + "，备注---》" + comment);
            ClincialResult accept = submitTreatmentService.acceptInfo(mdtId, creator, treatmentId, comment);
            return accept;
        }
    }

    @RequestMapping("/group")
    public ClincialResult groupDecision(Integer mdtId, Integer creator) {
        if (mdtId == null) {
            throw new NullException("请传入mdtId!!!");
        } else {
            ClincialResult group = submitTreatmentService.groupInfo(mdtId, creator);
            return group;
        }
    }

    @RequestMapping("/updateGroup")
    public ClincialResult updateGroup(Integer mdtId, Integer creator, Integer treatmentId) {
        if (mdtId == null||creator==null) {
            throw new NullException("传入了空数据!!!");
        } else {
            ClincialResult Group = submitTreatmentService.insertGroup(mdtId, creator, treatmentId);
            return Group;
        }
    }

    @RequestMapping("/TreatmentVote")
    public ClincialResult TreatmentVote(Integer mdtId) {
        if (mdtId == null) {
            throw new NullException("请传入mdtId");
        } else {
            ClincialResult vote = submitTreatmentService.returnVote(mdtId);
            return vote;
        }
    }

    @RequestMapping("/remarkInfo")
    public ClincialResult groupDecision(Integer mdtId) {
        //判断前端传过来的数据
        if (mdtId == null) {
            throw new NullException("传入的mdtId为空!!!");
        } else {
            JSONObject jsonObject = remarkNameService.RemarkInfo(mdtId);
            return ClincialResult.build(2000,"返回数据成功",jsonObject);
        }
    }
}
