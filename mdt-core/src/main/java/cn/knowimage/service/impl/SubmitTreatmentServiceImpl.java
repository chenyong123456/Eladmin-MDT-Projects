package cn.knowimage.service.impl;

import cn.knowimage.exception.NullException;
import cn.knowimage.mapper.AcceptTreatmentMapper;
import cn.knowimage.mapper.GroupTreatmentMapper;
import cn.knowimage.mapper.SubmitTreatmentMapper;
import cn.knowimage.pojo.instance.GroupDecision;
import cn.knowimage.pojo.instance.PersonalDecision;
import cn.knowimage.pojo.vo.GroupWechatTreatment;
import cn.knowimage.pojo.vo.SubmitTreament;
import cn.knowimage.pojo.vo.SumVote;
import cn.knowimage.pojo.vo.Votes;
import cn.knowimage.utils.ClincialResult;
import cn.knowimage.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitTreatmentServiceImpl {
    @Autowired
    private SubmitTreatmentMapper submitTreatmentMapper;
    @Autowired
    private AcceptTreatmentMapper acceptTreatmentMapper;
    @Autowired
    private GroupTreatmentMapper groupTreatmentMapper;
    @Autowired
    RemarkNameServiceImpl remarkNameService;
    //查询治疗方案并提交给前端的方法
    public ClincialResult submitTreament(Integer mdtId, Integer creator) {
        Integer count = submitTreatmentMapper.selectPersioncount(mdtId, creator);
        if (count > 0 && count < 3) {
            return ClincialResult.build(2000, "用户已完成决策");
        } else if (count > 3) {
            return ClincialResult.build(5005, "用户数据异常，多次决策");
        } else {
            List<SubmitTreament> na = submitTreatmentMapper.selectTreatmentWay(mdtId);
            System.out.println("治疗方案集合中的数据----》" + na);
            return ClincialResult.build(200, "用户未决策，治疗方式返回成功", na);
        }
    }
    //个人决策将数据插入数据库方法
    public ClincialResult acceptInfo(Integer mdtId, Integer creator, String treatmentId, String comment) {
        //return ClincialResult.build(4004, "用户不存在，可以进行决策");
        // 根据mdtid查出疾病的id并插入
        Integer tumorId = acceptTreatmentMapper.selectTumorByMdtId(mdtId);
        PersonalDecision pers = new PersonalDecision();
        List<String> lists = JsonUtils.jsonToList(treatmentId, String.class);
        //遍历治疗方式id并插入
        for (String pe : lists) {
            pers.setMdtId(mdtId);
            pers.setCreator(creator);
            if (NumberUtils.isNumber(pe)) {
                pers.setTreatmentId(Integer.parseInt(pe));
            } else {
                return ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR, "failed");
            }
            pers.setTumorId(tumorId);
            pers.setRemark(comment);
            acceptTreatmentMapper.insertSelective(pers);
            System.out.println("治疗方式的集合------------------》"+lists);
            if(lists.size()==1){
                //当只有一个治疗方式是直接插入优先级高的
                //System.out.println("走if了！！！");
                acceptTreatmentMapper.updatePriority("high",creator,Integer.parseInt(lists.get(0)));
            }else {
                //当有两个治疗方式时，先插入优先级高的，在插入优先级低的
                if (lists.get(0).equals(pe)){

                    acceptTreatmentMapper.updatePriority("high",creator,Integer.parseInt(lists.get(0)));
                }else{
                    System.out.println("走else里的else了！！！！");
                    acceptTreatmentMapper.updatePriority("low",creator,Integer.parseInt(lists.get(1)));
                }
            }
        }
        return ClincialResult.build(200, "提交成功");
    }

    //群组决策返回前端治疗方式和备注的方法
    public ClincialResult groupInfo(Integer mdtId,Integer creator) {
        //先判断是否所有参与投票的人都投票了
        System.out.println("mdtId--->" + mdtId);
        Integer mdtcount = groupTreatmentMapper.selectMdtcount(mdtId);
        Integer acceptcount = groupTreatmentMapper.selectAcceptcount(mdtId);
        System.out.printf("mdt参与投票人数----》" + mdtcount + ",已完成个人决策人数-------》" + acceptcount);
        if (mdtcount > acceptcount) {
            return ClincialResult.build(4005, "个人决策未全部提交");
        } else if (mdtcount < acceptcount) {
            return ClincialResult.build(4005, "投票异常，已投票人数大于应参加人数");
        } else {
            //创建一个最外层的集合
            List<String> idList=groupTreatmentMapper.selectIdList(mdtId,creator);
            System.out.println("判断是否已经做过群组决策的集合---》"+idList);
            if (!idList.isEmpty()){
                return ClincialResult.build(4004,"用户已做过群组决策");
            }else {
                List maxlist = new ArrayList<>();
                List<GroupWechatTreatment> list = acceptTreatmentMapper.selectGroupTreatment(mdtId);
                System.out.println("群组决策中的治疗方式集合---》" + list);
                int a = 0;
                JSONObject treatment = new JSONObject();
                for (GroupWechatTreatment gr : list) {
                    System.out.println("参加决策每个治疗方案对应的医生的id:" + gr.getId());
                    //存放医生名字的json对象
                    JSONObject treatmentJson = new JSONObject();
                    //往json对象中存放治疗方案的名字和医生名字
                    treatmentJson.put("highDoctorName", acceptTreatmentMapper.selectDoctorName(gr.getId(), mdtId, "high"));
                    treatmentJson.put("lowDoctorName", acceptTreatmentMapper.selectDoctorName(gr.getId(), mdtId, "low"));
                    treatmentJson.put("TreatmentName", gr.getName());
                    treatmentJson.put("TreatmentNameId", gr.getId());
                    a++;
                    treatment.put(a, treatmentJson);
                }
                maxlist.add(treatment);
                List<GroupWechatTreatment> doctorNameByMdt = acceptTreatmentMapper.selectDoctorNameByMdt(mdtId);
                //获取所有的医生姓名和备注
                JSONObject json =remarkNameService.RemarkInfo(mdtId);
                maxlist.add(json);
                return ClincialResult.build(200, "已存入数据库", maxlist);
            }
            }
    }
    //插入群组决策数据的方法
    public ClincialResult insertGroup(Integer mdtId,Integer creator,Integer treatmentId){
        GroupDecision groupDecision =new GroupDecision();
        groupDecision.setCreator(creator);
        groupDecision.setMdtId(mdtId);
        groupDecision.setTreatmentId(treatmentId);
        Integer tumorId = acceptTreatmentMapper.selectTumorByMdtId(mdtId);
        groupDecision.setTumorId(tumorId);
        List<Votes> votesList=groupTreatmentMapper.selectWeightings(mdtId,creator,treatmentId);
        System.out.println("查询出来的投票集合----》"+votesList);
        for (Votes vo:votesList) {
            if (vo.getPriority().equals("high")){
                Double votes=vo.getWeightings()*1.5;
                groupDecision.setVotes(votes);
                System.out.println("高优先级最后得到的总票数----》"+votes);
            }else{
                System.out.println("权重是-------》"+vo.getWeightings());
                Double votes=vo.getWeightings()*1.0;
                groupDecision.setVotes(votes);
                System.out.println("低优先级最后得到的总票数----》"+votes);
            }
        }
        try {
            groupTreatmentMapper.insertSelective(groupDecision);
        }catch (Exception e){
            throw new NullException("数据库数据异常！！！！");
        }
        return ClincialResult.build(200,"数据库更新成功");
    }

    //返回前端每个治疗方案的票数
    public ClincialResult returnVote(Integer mdtId){
        //用一个集合装所有的治疗方式和票数
        List<SumVote> list=groupTreatmentMapper.selectNameVote(mdtId);
        //创建一个外层对象
        JSONObject votes=new JSONObject();
        for (SumVote su:list){
            JSONObject nameVote=new JSONObject();
            nameVote.put("treatMent",su.getName());
            nameVote.put("votes",su.getVotes());
            votes.put(su.getName(),nameVote);
        }
        return ClincialResult.build(2000,"返回票数成功",votes);
    }
}
