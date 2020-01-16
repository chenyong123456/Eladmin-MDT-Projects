package cn.knowimage.wechat.mdtcorrelation.service.impl;


import cn.knowimage.ims.group.GroupExtension;
import cn.knowimage.ims.vo.User;
import cn.knowimage.instance.Department;
import cn.knowimage.instance.MdtTnm;
import cn.knowimage.mdt.mapper.MDTMapper;
import cn.knowimage.mdt.mapper.MdtTnmMapper;
import cn.knowimage.mdt.mapper.UserMapper;
import cn.knowimage.mdt.patienthistory.mapper.*;
import cn.knowimage.mdt.patienthistory.pojo.*;
import cn.knowimage.wechat.mdtcorrelation.service.IfAcceptService;
import cn.knowimage.instance.MDT;
import cn.knowimage.mdt.mapper.DepartmentMapper;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断用户是否接收该MDT的会议的相关业务逻辑
 * @author yong.Mr
 * @date 2020-1-7
 * @version 1.0.0
 */
@Transactional          
@Service
public class IfAcceptServiceImpl implements IfAcceptService {
    /**
     * 注入departmentMapper
     */
    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private UserMapper userMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private MdtTnmMapper mdtTnmMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private MDTMapper mdtMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private FamilyHistoryMapper familyHistoryMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private HistoryOfPresentIllnessMapper historyOfPresentIllnessMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private IrritabilityHistoryMapper irritabilityHistoryMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private LivingHistoryMapper livingHistoryMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private OperationHistoryMapper operationHistoryMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private OperationTypeMapper operationTypeMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private PastHistoryMapper pastHistoryMapper;
    /**
     * 注入departmentMapper
     */
    @Autowired
    private PatientMessageMapper patientMessageMapper;

    /**
     * 推荐给其他人, 查询该科室中的其他人的列表
     * @param uid
     * @param mid
     * @return
     */
    @Override
    public ClincialResult recommendOther(Integer uid, Integer mid, String groupId) {
        //获取if_accept != 1 的用户列表
        Example example1 = new Example(MdtTnm.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("mdt_id", mid);
       // criteria1.andEqualTo("if_accept", 0);
        List<MdtTnm> mdtTnms = mdtTnmMapper.selectByExample(example1);

        if (mdtTnms.size() == 0) {
            return ClincialResult.build(300, "你是该科室最后一个用户你必须参加该MDT会议!", new ArrayList<>());
        }
        //拿user_id去user表中查询相应的信息
        List<User> result = new ArrayList<>();

        //获取登录用户所在的科室
        User userDepart = new User();
        userDepart.setId(uid);
        User userDepartId = userMapper.selectByPrimaryKey(userDepart);
        Integer departId = 0;
        if (userDepartId != null) {
            System.out.println("查询出来的登录用户的信息----->:" + userDepartId.toString());
            departId = userDepartId.getJob_id();
            System.out.println("查询出来的departmentId----->:" + departId);
        }

        User user = null;
        for (MdtTnm mdtTnm1 : mdtTnms) {
            user = new User();
            System.out.println("传入的数据条件----->:" + mdtTnm1.getUser_id());
            user.setId(mdtTnm1.getUser_id());
            User user1 = userMapper.selectByPrimaryKey(user);
            System.out.println("check查询出来的数据----->:" + user1.toString());
            if (user1.getJob_id() == departId && user1.getId() != uid) {
                result.add(user1);
            }
        }

        return ClincialResult.build(200, "查询其他成员成功!", result);
    }

    /**
     * 推荐给其他人,点击了其他人的业务逻辑
     * @param uid
     * @param mid
     * @return
     */
    @Override
    public ClincialResult recommendOtherAgree(Integer uid, Integer mid, String groupId, Integer recommendPeopleId) {
        // uid为登陆人的id
        //更新该用户相应状态的值, 并根据相应的值进行判断.

        Example example = new Example(MdtTnm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mdt_id", mid);
        criteria.andEqualTo("user_id", uid);
        MdtTnm mdtTnm = new MdtTnm();
        mdtTnm.setIf_accept(0); // 代表该登录人将参与权限已经推荐给其他人, 0 改为参观者
        int flag = mdtTnmMapper.updateByExampleSelective(mdtTnm, example);

        System.out.println("**************************************recommendPeopleId推荐人ID是否传入进来**************************************");
        System.out.println("推荐其他人的ID----->:" + recommendPeopleId);
        System.out.println("**************************************recommendPeopleId推荐人ID是否传入进来**************************************");
        Example example2 = new Example(MdtTnm.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("mdt_id", mid);
        criteria2.andEqualTo("user_id", recommendPeopleId);
        MdtTnm mdtTnm2 = new MdtTnm();
        mdtTnm2.setIf_accept(1); // 代表该登录人将参与权限已经推荐给其他人, 将其他人改为1(被推荐的人)
        int flag2 = mdtTnmMapper.updateByExampleSelective(mdtTnm2, example2);

        if (flag2 != 1) {
            return ClincialResult.build(500, "数据更新失败!", null);
        }

        return ClincialResult.build(200, "推荐其他人成功!", null);
    }

    /**
     * 改方法为被推荐的人拒绝参入MDT会议
     * @param mdtId
     * @param uid
     * @param groupId
     * @return ClincialResult
     * uid, mid, groupId
     */
    @Override
    public ClincialResult recommendPeopleRefuse(int uid, int mdtId, String groupId) {
        // 将登陆人的id的状态改为 0
        Example example = new Example(MdtTnm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mdt_id", mdtId);
        criteria.andEqualTo("user_id", uid);
        MdtTnm mdtTnm = new MdtTnm();
        mdtTnm.setIf_accept(0); // 代表该登录人将参与权限已经推荐给其他人, 0 改为参观者
        int flag = mdtTnmMapper.updateByExampleSelective(mdtTnm, example);
        System.out.println("修改人的状态为----->:" + flag);
        // 更新成功!
            // 在这里进行将该科室主任的状态有0, 变为2, 具有推荐权限
            User userDepart = new User();
            userDepart.setId(uid);
            // 获取登录用户的科室ID
            User userByDepartId = userMapper.selectByPrimaryKey(userDepart);
            // 拿着科室id去查询
            Integer job_id = userByDepartId.getJob_id();
            Department department = new Department();
            department.setId(job_id);
            Department departmentHead = departmentMapper.selectByPrimaryKey(department);
            // 获取科室的主任user_id
            Integer inviter = departmentHead.getInviter();
            // 利用科室主任ID, 去更新mdt_tnm表的if_accept
            Example example2 = new Example(MdtTnm.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("mdt_id", mdtId);
            criteria2.andEqualTo("user_id", inviter);
            MdtTnm mdtTnm2 = new MdtTnm();
            mdtTnm2.setIf_accept(2); // 代表该登录人科室主任的if_accept的状态改为2, 具有推荐权限
            int flag2 = mdtTnmMapper.updateByExampleSelective(mdtTnm2, example2);
            if(flag2 != 1){
                ClincialResult.build(500,"refuse修改状态失败!",null);
            }
            return ClincialResult.build(200,"refuse成功!",null);

    }
    /**
     * 同意该场MDT的相关业务逻辑 -----> 请修改mdt_tnm的if_accept的状态为3, 以同意的状态 ***** 并且将其解除禁言
     * @param uid 该用户登录的id
     * @param mid 所要操作的mdt的id
     * @param groupId 所要操作的群组ID
     * @return ClincialResult
     */
    @Override
    public ClincialResult agreeMdt(Integer uid, Integer mid, String groupId) {
        //获取登录用户所在的科室
        User userUsername = new User();
        userUsername.setId(uid);
        User userName = userMapper.selectByPrimaryKey(userUsername);

        Example example = new Example(MdtTnm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mdt_id", mid);
        criteria.andEqualTo("user_id",uid);
        MdtTnm mdtTnm = new MdtTnm();
        // 将该科室的主任If_accep该为3, 具有参与者的权限, 和具有投票的权限
        mdtTnm.setIf_accept(3);
        int flag = mdtTnmMapper.updateByExampleSelective(mdtTnm, example);
        System.out.println("将该成员进行解除禁言!");
        // 第一种方法
        // 删除腾讯云群组中的用户 IfAcceptService.deleteGroupMember 变量放入了IfAcceptService中去了
        // GroupExtension.deleteGroupMember(users,"",IfAcceptService.deleteGroupMember);
        // 第二种方法 ----->解除该成员的禁言
        Boolean flagResult = GroupExtension.forbidSendMsg(userName, groupId);
        if (flagResult) {
            return ClincialResult.build(200, "成功!", null);
        }
        return ClincialResult.build(500, "失败!", null);
    }

    /**
     * 获取病人相关信息, 及相关病史
     * @param mdtId
     * @param uid   登陆人的ID
     * @return ClincialResult
     */
    @Override
    public ClincialResult getPeopleMsg(int mdtId, int uid) {
        System.out.println("****************************查询病人的用户信息****************************");

        JSONObject resultDisease = new JSONObject();
        //在这里写相关的逻辑 ---> 获取该场mdt的相关信息, 根据病人的PATIENT_ID可以获取病人的相关信息
        MDT mdt = new MDT();
        mdt.setId(mdtId);
        MDT mdt1 = mdtMapper.selectByPrimaryKey(mdt);
        System.out.println("是否获取到了该MDT的相关信息----->:" + mdt1);
        //根据mdt1获取病人ID取病人表中获取有关病人的基本信息
        int patientId = mdt1.getPatient_id();

        // 获取病人的相关信息
        Example examples = new Example(PatientMessage.class);
        Example.Criteria criterias = examples.createCriteria();
        criterias.andEqualTo("mdtId",mdtId);
        PatientMessage patientMessage = patientMessageMapper.selectOneByExample(examples);
       // resultDisease.put("patientBaseMessage",patientMessage);
        resultDisease.put("patientName", patientMessage.getPatientName());
        resultDisease.put("age", patientMessage.getAge());
        resultDisease.put("sex", patientMessage.getSex());
        resultDisease.put("phone", patientMessage.getPhone());

        //接下来获取病人的星官病史信息-----> 获取现病史表
        Example example = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mdt_id", mdtId);
        HistoryOfPresentIllness historyOfPresentIllness = this.historyOfPresentIllnessMapper.selectOneByExample(example);
        System.out.println("从数据库中是否查询出来现代史病史的数据----->:" + historyOfPresentIllness.toString());
        resultDisease.put("HistoryOfPresentIllness", historyOfPresentIllness);

        //获取手术史信息
        List<HistoryAndType> historyAndType = this.operationHistoryMapper.getOperationHistory(mdtId);
        JSONArray andType = new JSONArray();
        // 将遍历出来的数据以对象的形式放入到数组中
        for (int i = 0; i < historyAndType.size(); i++) {
            andType.add(historyAndType.get(i));
        }
        resultDisease.put("OperationHistory", andType);
        System.out.println("最终得到病人的手术信息----->:" + andType.toString());

        //获取既往史病例的信息
        Example example1 = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("mdt_id", mdtId);
        PastHistory pastHistory = this.pastHistoryMapper.selectOneByExample(example1);
        resultDisease.put("PastHistory", pastHistory);

        // 获取家族史的信息
        Example example2 = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("mdt_id", mdtId);
        FamilyHistory familyHistory = this.familyHistoryMapper.selectOneByExample(example2);
        resultDisease.put("FamilyHistory", familyHistory);

        // 获取过敏史的相关信息
        Example example3 = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("mdt_id", mdtId);
        IrritabilityHistory irritabilityHistory = this.irritabilityHistoryMapper.selectOneByExample(example3);
        resultDisease.put("IrritabilityHistory", irritabilityHistory);

        // 获取生活史的相关信息
        Example example4 = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteria4 = example4.createCriteria();
        criteria4.andEqualTo("mdt_id", mdtId);
        LivingHistory livingHistory = this.livingHistoryMapper.selectOneByExample(example4);
        resultDisease.put("LivingHistory", livingHistory);

        System.out.println("****************************查询病人的用户信息****************************");
        System.out.println("查询出病人的最终信息----->:" + resultDisease.toString());
        System.out.println("****************************查询病人的用户信息****************************");
        return ClincialResult.build(200, "查询病人病史成功!", resultDisease);
    }


}
