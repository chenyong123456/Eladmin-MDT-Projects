package cn.knowimage.mdt.createmdt.service.impl;


import cn.knowimage.ims.vo.User;
import cn.knowimage.instance.*;
import cn.knowimage.mdt.mapper.*;
import cn.knowimage.mdt.patienthistory.mapper.*;
import cn.knowimage.mdt.patienthistory.pojo.*;
import cn.knowimage.utils.ClincialResult;
import cn.knowimage.mdt.createmdt.service.CRUDService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;

/**
 * 对MDT的增删改查的接口的实现类
 * @author yong.Mr
 * @version 1.0
 * @Date 2019-12-31
 */
@Transactional
@Service
public class CRUDServiceImpl implements CRUDService {
    /**
     * 注入mdt的Mapper层的数据
     */
    @Autowired
    private MDTMapper mdtMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    ;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MdtTnmMapper mdtTnmMapper;

    @Autowired
    private FamilyHistoryMapper familyHistoryMapper;

    @Autowired
    private HistoryOfPresentIllnessMapper historyOfPresentIllnessMapper;

    @Autowired
    private IrritabilityHistoryMapper irritabilityHistoryMapper;

    @Autowired
    private LivingHistoryMapper livingHistoryMapper;

    @Autowired
    private OperationHistoryMapper operationHistoryMapper;

    @Autowired
    private OperationTypeMapper operationTypeMapper;

    @Autowired
    private PastHistoryMapper pastHistoryMapper;

    @Autowired
    private PatientMessageMapper patientMessageMapper;

    @Autowired
    private MdtDepartMapper mdtDepartMapper;

    @Autowired
    private MdtDisMapper mdtDisMapper;

    @Autowired
    private ImageInfoMapper imageInfoMapper;

    @Autowired
    private GoalTemplateMapper goalTemplateMapper;
    /**
     * 删除MDT的主要业务逻辑
     * 注意: 该删除数据是真的删除数据库中是数据
     *
     * @param mid    MDT的ID
     * @param userId 用户的ID
     * @return ClincialResult
     */
    @Override
    public ClincialResult deleteMdt(Integer mid, Integer userId) {


        // 删除mdt_tnm表中相关mdt的相关数据
        Example exampleTnm = new Example(MdtTnm.class);
        Example.Criteria criteriaTnm = exampleTnm.createCriteria();
        criteriaTnm.andEqualTo("mdt_id", mid);
        int tnmFlag = mdtTnmMapper.deleteByExample(exampleTnm);

        // 删除mdt_depart表中相关mdt的相关数据
        Example exampleMdTDepart = new Example(MdtDepart.class);
        Example.Criteria criteriaMdTDepart = exampleMdTDepart.createCriteria();
        criteriaMdTDepart.andEqualTo("mdt_id", mid);
        int mdtDepartFlag = mdtDepartMapper.deleteByExample(exampleMdTDepart);

        // 删除mdt_dis表中相关mdt的相关数据
        Example exampleDis = new Example(MdtDis.class);
        Example.Criteria criteriaDis = exampleDis.createCriteria();
        criteriaDis.andEqualTo("mdtId", mid);
        int DisFlag = mdtDisMapper.deleteByExample(exampleDis);

        // 删除现病史HISTORY_OF_PRESENT_ILLNESS表中相关mdt的相关数据
        Example examplePresentHistory = new Example(HistoryOfPresentIllness.class);
        Example.Criteria criteriaPresentHistory = examplePresentHistory.createCriteria();
        criteriaPresentHistory.andEqualTo("mdt_id", mid);
        int presentHistoryFlag = historyOfPresentIllnessMapper.deleteByExample(examplePresentHistory);

        // 删除手术史OPERATION_HISTORY表中相关mdt的相关数据
        Example exampleOperationHistory = new Example(OperationHistory.class);
        Example.Criteria criteriaOperationHistory = exampleOperationHistory.createCriteria();
        criteriaOperationHistory.andEqualTo("mdt_id", mid);
        int operationHistoryFlag = operationHistoryMapper.deleteByExample(exampleOperationHistory);

        // 删除影像资料IMAGE_INFO表中相关mdt的相关数据
        Example exampleImageInfo = new Example(ImageInfo.class);
        Example.Criteria criteriaImageInfo = exampleImageInfo.createCriteria();
        criteriaImageInfo.andEqualTo("iMdtId", mid);
        int ImageInfoFlag = imageInfoMapper.deleteByExample(exampleImageInfo);

        // 删除既往史PAST_HISTORY表中相关mdt的相关数据
        Example examplePastHistory = new Example(PastHistory.class);
        Example.Criteria criteriaPastHistory = examplePastHistory.createCriteria();
        criteriaPastHistory.andEqualTo("mdt_id", mid);
        int PastHistoryFlag = pastHistoryMapper.deleteByExample(examplePastHistory);

        // 删除家族史FAMILY_HISTORY表中相关mdt的相关数据
        Example exampleFamilyHistory = new Example(FamilyHistory.class);
        Example.Criteria criteriaFamilyHistory = exampleFamilyHistory.createCriteria();
        criteriaFamilyHistory.andEqualTo("mdt_id", mid);
        int familyHistoryFlag = familyHistoryMapper.deleteByExample(exampleFamilyHistory);

        // 删除过敏史IRRITABILITY_HISTORY表中相关mdt的相关数据
        Example exampleIrritabilityHistory = new Example(IrritabilityHistory.class);
        Example.Criteria criteriaIrritabilityHistory = exampleIrritabilityHistory.createCriteria();
        criteriaIrritabilityHistory.andEqualTo("mdt_id", mid);
        int IrritabilityHistoryFlag = irritabilityHistoryMapper.deleteByExample(exampleIrritabilityHistory);

        // 删除生活史LIVING_HABBIT表中相关mdt的相关数据
        Example exampleLivingHabbit = new Example(LivingHistory.class);
        Example.Criteria criteriaLivingHabbit = exampleLivingHabbit.createCriteria();
        criteriaLivingHabbit.andEqualTo("mdt_id", mid);
        int LivingHabbitFlag = livingHistoryMapper.deleteByExample(exampleLivingHabbit);

        // 删除该mdt的病人信息PATIENT_MESSAGE表中相关mdt的相关数据
        Example examplePatientMessage = new Example(PatientMessage.class);
        Example.Criteria criteriaPatientMessage = examplePatientMessage.createCriteria();
        criteriaPatientMessage.andEqualTo("mdtId", mid);
        int patientMessageFlag = patientMessageMapper.deleteByExample(examplePatientMessage);

        // 删除MDT表中的MDT的数据
        MDT mdt = new MDT();
        mdt.setId(mid);
        int mdtFlag = mdtMapper.deleteByPrimaryKey(mdt);
        System.out.println("删除mdt时否成功----->:" + mdtFlag);

        if (mdtFlag == 1 && tnmFlag == 1 && mdtDepartFlag == 1 && DisFlag == 1 && presentHistoryFlag == 1 && operationHistoryFlag == 1 &&
                ImageInfoFlag == 1 && PastHistoryFlag == 1 && familyHistoryFlag == 1 && IrritabilityHistoryFlag == 1 && LivingHabbitFlag == 1 &&
                patientMessageFlag == 1) {

            return ClincialResult.build(HttpStatus.SC_OK, "success", null);
        }
        return ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR, "success", null);
    }

    /**
     * 创建mdt的目的模板
     * @param mdtTemp 前台传过来的数据
     * @return ClincialResult
     */
    @Override
    public ClincialResult createMdtGoal(String mdtTemp) {
        // 将前台传过来的数据进行反序列化
        JSONArray mdtTem = JSONArray.fromObject(mdtTemp);
        System.out.println("测试----->:" + mdtTem.toString());
        GoalTemplate goalTemplate = null;
        try {
            // 循环insert数据, 并返回自动递增的主键并返回给大哥
            for (int i = 0; i < mdtTem.size(); i++) {
                goalTemplate = new GoalTemplate();
                goalTemplate.setMdtGoal(mdtTem.getJSONObject(i).getString("value"));

                if(!mdtTem.getJSONObject(i).containsKey("id")) {
                    //没有id, 请进入insert操作
                    goalTemplate.setCreateTime(new Date());
                    int flagInsert = goalTemplateMapper.insertSelective(goalTemplate);
                    if(flagInsert != 1){
                        return ClincialResult.build(HttpStatus.SC_BAD_REQUEST,"FAIL",null);
                    }
                }else {
                    // 有id进行修改操作
                    goalTemplate.setId(mdtTem.getJSONObject(i).getInt("id"));
                    goalTemplate.setModifyTime(new Date());
                    int flagUpdate = goalTemplateMapper.updateByPrimaryKeySelective(goalTemplate);
                    if(flagUpdate != 1){
                        return ClincialResult.build(HttpStatus.SC_BAD_REQUEST,"FAIL",null);
                    }
                }

            }
        }catch (Exception e){
            return ClincialResult.build(HttpStatus.SC_BAD_REQUEST,"FAIL",null);
        }
        return ClincialResult.build(HttpStatus.SC_OK,"success",null);
    }
    /**
     * 查询MDT的主导科室和其他科室的数据列表
     * @param uid 登录的用户的id
     * @return
     */
    @Override
    public ClincialResult getMdtRegionOrOther(Integer uid) {
        // new一个对象JSONObject
        JSONObject result = new JSONObject();
        // 加入数组的相关数据
        List<GoalTemplate> goalTemplates = null;
        ClincialResult mdtGoalTemplate = null;
        try {
            // 查询数据库
            goalTemplates = goalTemplateMapper.selectAll();
            System.out.println("查询MDT的模板数据列表----->:" + goalTemplates.toString());
            mdtGoalTemplate = CRUDServiceImpl.getMdtGoalTemplate(goalTemplates);
        }catch (Exception e){
            return ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR,"FAIL", goalTemplates);
        }
        // 调用其他实现方法
        System.out.println("mdtGoalTem返回给前台的数据格式----->:" + mdtGoalTemplate.getData().toString());
        // getMdtGoalList获取mdt的目的模板
        result.put("mdtGoalTem", mdtGoalTemplate.getData());

        // 查询主导科室
        Example example = new Example(Department.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("id",1);
        List<Department> departments = departmentMapper.selectByExample(example);
        System.out.println("查询科室的数据为----->:" + departments.toString());
        // collection == null || collection.isEmpty();  避免departments.size()出现空指针异常
        if(CollectionUtils.isEmpty(departments)){
            ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR,"FAILE", null);
        }
        // 加入mdt主导科室模板数据
        JSONArray regionArray = new JSONArray();
        JSONObject region = null;
        for (int i = 0; i < departments.size(); i++) {
            region = new JSONObject();
            region.put("value",departments.get(i).getName());
            region.put("label",departments.get(i).getName());
            regionArray.add(region);
        }
        // 将主导科室数据列表加入到对象中
        result.put("regionArray",regionArray);
        System.out.println("查询mdt主导科室模板数据----->:" + regionArray);

        // 查询用户所在的科室
        User user = new User();
        user.setId(uid);
        User userDenlu = userMapper.selectByPrimaryKey(user);
        System.out.println("登录人的相关信息----->:" + userDenlu.toString());
        JSONArray otherDepartmentArray = new JSONArray();
        JSONObject otherDepartmentObject = null;
        for (int i = 0; i < departments.size(); i++) {
            otherDepartmentObject = new JSONObject();
            otherDepartmentObject.put("name",departments.get(i).getName());
            otherDepartmentObject.put("id", departments.get(i).getName());
            otherDepartmentObject.put("status", true);
            otherDepartmentObject.put("state", true);
            if(departments.get(i).getId() != userDenlu.getJob_id()) {
                otherDepartmentObject.put("status", false);
                otherDepartmentObject.put("state", false);
            }
            otherDepartmentArray.add(otherDepartmentObject);
        }
        System.out.println("array----->:" + otherDepartmentArray.toString());
        // 将其他科室数据列表加入到对象中
        result.put("otherDepartmentArray",otherDepartmentArray);
        System.out.println("Object----->:" + result.toString());
        return ClincialResult.build(HttpStatus.SC_OK,"SUCCESS", result);
    }

    /**
     * 生成MDT模板的通用类
     * @param goalTemplates 查询出MDTgoal的所有的模板数据
     * @return ClincialResult
     */
    public static ClincialResult getMdtGoalTemplate(List<GoalTemplate> goalTemplates){
        // CollectionUtils.isEmpty(goalTemplates) = (collection == null || collection.isEmpty());
        // 先判断null, 如果不,否则会报空指针异常
        if(CollectionUtils.isEmpty(goalTemplates)){
            return ClincialResult.build(HttpStatus.SC_NOT_FOUND,"没有数据哦!", goalTemplates);
        }
        // new一个对象JSONObject
        JSONObject result = new JSONObject();
        // 添加其他数据到数组的第一条
        GoalTemplate goalTemplate2  = new GoalTemplate();
        goalTemplate2.setId((-1));
        goalTemplate2.setMdtGoal("其它描述");
        goalTemplates.add(0, goalTemplate2);
        // 加入mdt目的模板数据
        JSONArray goalTemplateArray = new JSONArray();
        JSONObject goalTemplateObject = new JSONObject();
        for (int i = 0; i < goalTemplates.size(); i++) {
            goalTemplateObject.put("id",goalTemplates.get(i).getId());
            goalTemplateObject.put("value",goalTemplates.get(i).getMdtGoal());
            goalTemplateArray.add(goalTemplateObject);
        }
        result.put("mdtGoalTem", goalTemplateArray);
        return ClincialResult.build(HttpStatus.SC_OK,"SUCCESS", goalTemplateArray);
    }
    /**
     * 获取mdtGoal的数据列表
     * @return ClincialResult
     */
    @Override
    public ClincialResult getMdtGoalList() {

        // 加入数组的相关数据
        List<GoalTemplate> goalTemplates = null;
        try {
            // 查询数据库
            goalTemplates = goalTemplateMapper.selectAll();
            System.out.println("查询MDT的模板数据列表----->:" + goalTemplates.toString());
            ClincialResult mdtGoalTemplate = CRUDServiceImpl.getMdtGoalTemplate(goalTemplates);
            return mdtGoalTemplate;
        }catch (Exception e){
            return ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR,"FAIL", goalTemplates);
        }
    }

    /**
     * 删除MDTGoal的列表数据
     * @param id 删除的id
     * @return ClincialResult
     */
    @Override
    public ClincialResult deleteMdtGoal(Integer id) {
        GoalTemplate goalTemplate = new GoalTemplate();
        goalTemplate.setId(id);
        int flag = goalTemplateMapper.deleteByPrimaryKey(goalTemplate);
        if(flag != 1){
            return ClincialResult.build(HttpStatus.SC_INTERNAL_SERVER_ERROR,"FAIL",null);
        }
        return ClincialResult.build(HttpStatus.SC_OK,"SUCCESS",null);
    }

    /**
     * 大哥PC端的本人创建MDT的列表
     * @param uid
     * @return
     */
    @Override
    public JSONArray getOnselfCreateMdtList(int uid) {
        JSONArray resultParticition = new JSONArray();
        JSONObject mdtList = null;
        //拿到该用户的所有MDT信息

        List<MDT> mdtListByUserId = mdtMapper.getOnselfCreateMdtList(uid);
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


}
