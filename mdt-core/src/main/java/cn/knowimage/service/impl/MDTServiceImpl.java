package cn.knowimage.service.impl;


import cn.knowimage.ims.group.GroupBody;
import cn.knowimage.ims.group.GroupExtension;
import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.ims.vo.User;
import cn.knowimage.mapper.*;
import cn.knowimage.pojo.instance.*;
import cn.knowimage.service.MDTService;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事务一般在service层加
 * @Transactional 可以作用于类 和 方法上
 * Transactional放在类级别上等同于该类的每个方法都放上了@Transactional
 * 默认将类中的所有函数纳入事务管理
 */
@Transactional
@Service
public class MDTServiceImpl implements MDTService {

    @Value("${batchImport}")
    private String batchImport;

    @Value("${whetherImport}")
    private String whetherImport;
    /**
     * 创建群组Url
     */
    @Value("${createGroup}")
    private String creategroups;

    @Autowired
    private MDTMapper mdtMapper;

    @Autowired
    private MdtTnmMapper mdtTnmMapper;

    @Autowired
    private MdtDisMapper mdtDisMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private MdtDepartMapper mdtDepartMapper;

    @Autowired
    private PatientMessageMapper patientMessageMapper;

    @Autowired
    private TumorTypeMapper tumorTypeMapper;

    @Autowired
    private GoalTemplateMapper goalTemplateMapper;

    /**
     * 该变量记录每次请求的用户连接图像
     */
    private static String oldUrl = "";

    /**
     * 返回的是小从微信小程序端的接口
     * @param uid 登录人的用户的ID
     * @param url 微信的图像
     * @return
     */
    @Override
    public JSONArray getMDTList(int uid, String url) {
        // 查询登录用户的相关信息
        User user = new User();
        user.setId(uid);
        User user1 = userMapper.selectByPrimaryKey(user);
        // 如果用户头像不相同则更新
        if(!"".equals(url)) {
            if (!oldUrl.equals(url)) {
                Boolean aBoolean = GroupExtension.importSingleMember(user1, url);
                System.out.println("图像是否导入成功----->:" + aBoolean);
                User userAvatar = new User();
                userAvatar.setId(uid);
                userAvatar.setWx_avatar(url);
                int flagAvatar = userMapper.updateByPrimaryKeySelective(userAvatar);
                if (flagAvatar != 1) {
                    System.out.println("更新微信图失败!");
                    // return null;
                }
            }
        }
        this.oldUrl = url;
        //拼接MDT的相关信息列表
        JSONArray resultList = new JSONArray();
        // 如果该用户被禁用, 该用户是不能看到数卡片
        if(user1.getEnabled() == 0){
            return resultList;
        }
        JSONObject mdtList = null;
        //拿到该用户的所有MDT信息
        List<MDT> mdtListByUserId = mdtMapper.getMDTList(uid, "", "");
        for (int i = 0; i < mdtListByUserId.size(); i++) {
            mdtList = new JSONObject();
            mdtList.put("id", mdtListByUserId.get(i).getId());
            mdtList.put("if_accept", mdtListByUserId.get(i).getIf_accept());
            mdtList.put("mdtname", mdtListByUserId.get(i).getName());
            mdtList.put("meeting_location", mdtListByUserId.get(i).getMeeting_location());

            // 获取病人的具体信息
            Example example1 = new Example(PatientMessage.class);
            Example.Criteria criteria1 = example1.createCriteria();
            System.out.println("测试mdt的id----->:" + mdtListByUserId.get(i).getId());
            criteria1.andEqualTo("mdtId", mdtListByUserId.get(i).getId());
            PatientMessage patientMessage = patientMessageMapper.selectOneByExample(example1);
            System.out.println("病人的基本信息是否查询出来----->:" + patientMessage.toString());
            // 病人的名字和年龄, 性别
            mdtList.put("patientname", mdtListByUserId.get(i).getPatient_name());
            mdtList.put("age", patientMessage.getAge());
            mdtList.put("sex", patientMessage.getSex());
            // 主治医生
            mdtList.put("mainphlycian", patientMessage.getDoctorMain());

            // 获取每个MDT对应的科室
            Department mdtDepart = mdtMapper.getMDTDepart(mdtListByUserId.get(i).getId());
            mdtList.put("department", mdtDepart.getName());  //主治科室
            mdtList.put("predict_time", mdtListByUserId.get(i).getPredict_time());
            mdtList.put("groupId", mdtListByUserId.get(i).getGroup_id());
            mdtList.put("goal", mdtListByUserId.get(i).getGoal());

            resultList.add(mdtList);
        }
        System.out.println("微信小程序的数据列表是----->:" + resultList.toString());

        return resultList;
    }

    public JSONArray getListCommon(List<MDT> mdtListByUserId) {

        return null;
    }
    /**
     * 大哥的eladmin后台的接口参与者的接口, 查询MDT列表
     * @param uid 登录人的id
     * @return JSONArray
     */
    @Override
    public JSONArray getMdtParticition(int uid) {
        // 查询相应的接口
        JSONArray resultParticition = new JSONArray();
        JSONObject mdtList = null;
        //拿到该用户的所有MDT信息
        List<MDT> mdtListByUserId = mdtMapper.getMDTParticitionList(uid);
        for (int i = 0; i < mdtListByUserId.size(); i++) {
            mdtList = new JSONObject();
            mdtList.put("id", mdtListByUserId.get(i).getId());
            mdtList.put("if_accept", mdtListByUserId.get(i).getIf_accept());
            mdtList.put("mdtname", mdtListByUserId.get(i).getName());
            mdtList.put("meeting_location", mdtListByUserId.get(i).getMeeting_location());

            // 获取病人的具体信息
            Example example1 = new Example(PatientMessage.class);
            Example.Criteria criteria1 = example1.createCriteria();
            System.out.println("测试mdt的id----->:" + mdtListByUserId.get(i).getId());
            criteria1.andEqualTo("mdtId", mdtListByUserId.get(i).getId());
            PatientMessage patientMessage = patientMessageMapper.selectOneByExample(example1);
            System.out.println("病人的基本信息是否查询出来----->:" + patientMessage.toString());
            // 病人的名字和年龄, 性别
            mdtList.put("patientname", mdtListByUserId.get(i).getPatient_name());
            mdtList.put("age", patientMessage.getAge());
            mdtList.put("sex", patientMessage.getSex());
            // 主治医生
            mdtList.put("mainphlycian", patientMessage.getDoctorMain());

            // 获取每个MDT对应的科室
            Department mdtDepart = mdtMapper.getMDTDepart(mdtListByUserId.get(i).getId());
            mdtList.put("department", mdtDepart.getName());  //主治科室
            mdtList.put("predict_time", mdtListByUserId.get(i).getPredict_time());
            mdtList.put("goal", mdtListByUserId.get(i).getGoal());


            resultParticition.add(mdtList);
        }
        System.out.println("大哥参与者的MDT的列表----->:" + resultParticition.toString());

        return resultParticition;

    }

    /**
     * 返回的是大哥PC端的接口 获取MDT列表的接口
     * @param uid 主要作分页
     * @return
     */
    @Override
    public JSONObject getMDTListByUser(int uid, int dataNumber, int page, String queryName, String queryConsultation) {
        System.out.println("Service层中page是否传入----->:" + page);
        JSONObject finallyResult = new JSONObject();
        JSONArray result = new JSONArray();
        JSONObject mdtResult = null;
        List<MDT> list = mdtMapper.getMDTListByUser(uid, dataNumber, page, queryName, queryConsultation);
        List<MDT> mdtList = mdtMapper.getMDTList(uid, queryName, queryConsultation);
        System.out.println("数据是否查询出来------>:" + list.toString());
        for (MDT mdt : list) {
            mdtResult = new JSONObject();
            mdtResult.put("id", mdt.getId());
            //System.out.println("从数据库中是否查询出predictTime----->:" + mdt.getPredict_time());
            mdtResult.put("time", mdt.getPredict_time());  //会议预期开始的时间
            mdtResult.put("name", mdt.getPatient_name());  //MDT创建人的名字
            Department mdtDepart = mdtMapper.getMDTDepart(mdt.getId());
            mdtResult.put("department", mdtDepart.getName());  //主导科室的名字
            mdtResult.put("consultation", mdt.getName());  //创建MDT的名字
            mdtResult.put("groupId", mdt.getGroup_id());
            mdtResult.put("url", mdt.getUrl_report());
            result.add(mdtResult);
        }
        System.out.println("**************************返回给前台的数据**************************");
        System.out.println("最终返回给前台的数据----->:" + result.toString());
        finallyResult.put("total", mdtList.size());
        finallyResult.put("mdtList", result);
        finallyResult.put("page", page); // 返回给前台页数
        return finallyResult;
    }

    /**
     * 注意：方法的@Transactional会覆盖类上面声明的事务
     * @param createMDTVo 前台传过来的总数据
     * @param uid         为创建人的人员Id -----> 需要放入到mdt_tnm中的USER_ID ----->及创建人的Id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    @Override
    public ClincialResult insertMDT(CreateMDTVo createMDTVo, int uid) {
        //createMDTVo为前台传过来的数据格式
        MDT mdt = new MDT();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        //把不为null的插入数据库中------>创建MDT, 插入要创建的数据信息到数据库
        mdt.setName(createMDTVo.getMdtName()); //创建MDT的名称
        mdt.setCreator_id(uid); // 创建者的姓名------>这里数据库改为了创建人的ID 类型为Integer类型
        if (createMDTVo.getIs_repeat()) {  //病例史是否复诊
            mdt.setIs_repeat(0);
        } else {
            mdt.setIs_repeat(1);
        }
        // 插入开会的目的
        if(createMDTVo.getGoal() == (-1)) {
            mdt.setGoal(createMDTVo.getGoal2());  // MDT开会的目的
        }else{
            // 从数据库中查询相应的目的模板
            GoalTemplate goalSelect = new GoalTemplate();
            goalSelect.setId(createMDTVo.getGoal());
            GoalTemplate goalResult = goalTemplateMapper.selectByPrimaryKey(goalSelect);
            // 将相应的MDT模板加入到mdt数据库中取
            mdt.setGoal(goalResult.getMdtGoal());
        }
        mdt.setCreate_time(date);  // MDT的创建时间
        String searchDate = "";
        if (!StringUtils.isBlank(createMDTVo.getTime()) && !StringUtils.isBlank(createMDTVo.getTime2())) {
            searchDate = createMDTVo.getTime() + " " + createMDTVo.getTime2();
            mdt.setPredict_time(createMDTVo.getTime() + " " + createMDTVo.getTime2());
        } else {
            searchDate = simpleDateFormat.format(date).toString();
            mdt.setPredict_time(simpleDateFormat.format(date));
        }
        mdt.setMeeting_location(createMDTVo.getLocation()); // 加入例会地点
        mdt.setWay(createMDTVo.getWay());  // 开会方式
        mdt.setPatient_id(0);
        mdt.setPatient_name(createMDTVo.getName());
        // StringUtils.isNumeric(""); 判断String是否是数据类型的值(不推荐使用)// ""StringUtils.isNumeric(null)= false StringUtils.isNumeric("")=true StringUtils.isNumeric("  ")=false
        // NumberUtils可以判断String是否是数据类型的字符串 ***** 推荐使用
        if (NumberUtils.isNumber(createMDTVo.getIsPays())) {
            mdt.setIs_self_paying(Integer.parseInt(createMDTVo.getIsPays()));
        } else {
            mdt.setIs_self_paying(0);
        }
        //需要病人id, 前台只传了病人姓名----->后期看是否可以通过后台拿到病人的Id
        System.out.println("要插入数据中的数据----->:" + mdt.toString());

        int i1 = mdtMapper.insertSelective(mdt);

        System.out.println("插入数据是否成功!----->:" + i1);

        // 获取insertSelective数据时返回的自动递增的主键
        System.out.println("**********************获取自动递增的主键insertSelective自动返回自动递增的主键到id上**************************");
        System.out.println("获取自动递增的主键----->:" + mdt.getId());
        MDT mdt1 = new MDT();
        mdt1.setId(mdt.getId());

        //获得刚创建的MDT去更新mdt_tnm表中的MDT_ID, uid是前台传过来用户ID, 既科室人员创建的ID
        System.out.println("查看要插入mdt_tnm表中的数据----->:" + uid + "   " + mdt1.getId());
        mdtMapper.insertMDT(uid, mdt1.getId());  //uid为创建人的id, 必须要写入mdt_tnm表中去

        //在这里创建群组

        List<User> importMemberAll = new ArrayList<>();
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            importMemberAll.add(user);
            // 登录人已经加入了mdt_tnm中, 在这里不需要加入, 加入不等于 -----> 该人所有权限为观察者0
            if (user.getId() != uid) {
                mdtMapper.insertMDT(user.getId(), mdt1.getId());
            }
        }
        // 导入所有腾讯云成员
        String groupAll = GroupBody.getGroupBody(createMDTVo, importMemberAll, batchImport, whetherImport).toString();
        String resultAll = HttpClientUtil.doPostJson(creategroups, groupAll);

        //根据前台传过来的科室列表进行查询需要该科室的主任,将其状态进行改变
        for (int i = 0; i < createMDTVo.getType().length; i++) {
            Example example3 = new Example(Department.class);
            Example.Criteria criteria3 = example3.createCriteria();
            System.out.println("查看参入科室----->:" + createMDTVo.getType()[i]);
            criteria3.andEqualTo("name", createMDTVo.getType()[i]);
            Department department = departmentMapper.selectOneByExample(example3);

            // 拿到该科室的主任ID
            Integer inviter = department.getInviter();

            //获取科室ID, 拿着科室ID , 去人员表中查相应的成员.
            System.out.println("查看科室的id----->:" + department.getId());
            //拿着departmentId去查询人员表
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("job_id", department.getId());
            //获取该科室的所有人员
            List<User> userDepMem = userMapper.selectByExample(example1);
            for (User user : userDepMem) {
                // 是这层循环科室的人----->并且找出是该科室的主任,将其mdt_tnm中的IF_ACCEPT修改为2具有推荐权
                // 登录人已经加入了mdt_tnm中, 在这里不需要加入, 加入不等于----->是科室
                if (department.getInviter() == user.getId()) {
                    Example example = new Example(MdtTnm.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("mdt_id", mdt.getId());
                    criteria.andEqualTo("user_id", user.getId());
                    MdtTnm mdtTnm = new MdtTnm();
                    // 将该科室的主任If_accep该为2, 具有推荐的权限
                    mdtTnm.setIf_accept(2);
                    int flag2 = mdtTnmMapper.updateByExampleSelective(mdtTnm, example);
                    if (flag2 != 1) {
                        return ClincialResult.build(500, "设置科室主任状态失败!", mdt);
                    }
                }
            }
        }
        // 开起指定人的发言权力
        //String group = GroupBody.getGroupBody(createMDTVo, importMember, batchImport, whetherImport).toString();
        //这里可以创建群组, group是创建群组, 改变body中的数据格式.
        //String result = HttpClientUtil.doPostJson(creategroups, group);
        System.out.println("返回请求创建群组的数据, 及响应体----->:" + resultAll);
        String cunGroupId = "";
        //获得群组GROUP_ID
        JSONObject GroupResponse = JSONObject.fromObject(resultAll);
        if ("OK".equals(GroupResponse.getString("ActionStatus"))) {
            //创建群组成功, 请将mdt表中的GROUP_ID进行更新为腾讯云返回的群组的ID
            String groupId = GroupResponse.getString("GroupId");
            cunGroupId = groupId;

            // 在这里进行群组中全员禁言----->请拿到groupId去全员禁言
            Boolean aBoolean = GroupBody.forbidSendMsg(importMemberAll, groupId);
            System.out.println("***************************是否禁言成功***************************");
            if (aBoolean) {
                System.out.println("是否禁言成功----->:" + aBoolean);
            }
            System.out.println("***************************是否禁言成功***************************");
            //可以获得刚创建MDT时的ID, 去更新MDT表中的GROUP_ID的字段, 代表该MDT讨论组是那一个组
            mdt1.setGroup_id(groupId);
            mdtMapper.updateByPrimaryKeySelective(mdt1);

            //在表mdt_dis中更新MDT所属于的病例信息
            MdtDis mdtDis = new MdtDis();
            mdtDis.setMdtId(mdt1.getId());
            System.out.println("查看前台TumorType数据是否成功----->:" + createMDTVo.getTumorType());
            // 查询出TUMOR_TYPE表的相关治疗方式的名字
            TumorType tumorType = new TumorType();
            tumorType.setId(createMDTVo.getTumorType());
            TumorType tumorType1 = tumorTypeMapper.selectByPrimaryKey(tumorType);
            // 获取用户病例信息
            mdtDis.setDisease(tumorType1.getName());
            // insert到数据库中mdt_dis表中的TUMOR_TYPE_ID的ID
            mdtDis.setTumor_type_id(createMDTVo.getTumorType());
            int insert = mdtDisMapper.insert(mdtDis);//不使用数据库中的默认值
            System.out.println("insert的数据到表mdt_dis是");

            //更新相应的mdt_depart表中的信息
            MdtDepart mdtDepart = new MdtDepart();
            //补全元素
            mdtDepart.setMdt_id(mdt1.getId());  // 补全MDT_ID
            //根据前台传过来主导科室来获取, 主导科室的id
            Example example2 = new Example(Department.class);
            Example.Criteria criteria2 = example2.createCriteria();
            //主导科室的name的名字
            criteria2.andEqualTo("name", createMDTVo.getRegion());
            Department department = departmentMapper.selectOneByExample(example2);
            System.out.println("测试查询科室数据的Id----->: " + department.toString());
            //设置科室Id
            mdtDepart.setHead_id(department.getId());  // 补全主导科室ID
            //mdtDepart.setMdt_id(mdt1.getId());
            mdtDepart.setIs_guiding(1);
            int i = mdtDepartMapper.insertSelective(mdtDepart);

        }

        return ClincialResult.build(200, "创建群组成功!", mdt);
    }



}
