package cn.knowimage.mdt.patienthistory.service.impl;



import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.instance.MDT;
import cn.knowimage.mdt.patienthistory.mapper.*;
import cn.knowimage.mdt.patienthistory.pojo.*;
import cn.knowimage.mdt.patienthistory.service.IllnessHistoryService;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONArray;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对病人的相关病信息的相关操作和业务逻辑
 * @author yong.Mr
 * @version 1.0
 * @date 2019-12-18
 */
@Service
public class IllnessHistoryServiceImpl implements IllnessHistoryService {

    /**
     * 注入patientMessageMapper
     */
    @Autowired
    private PatientMessageMapper patientMessageMapper;
    /**
     * 注入familyHistoryMapper
     */
    @Autowired
    private FamilyHistoryMapper familyHistoryMapper;
    /**
     * 注入historyOfPresentIllnessMapper
     */
    @Autowired
    private HistoryOfPresentIllnessMapper historyOfPresentIllnessMapper;
    /**
     * 注入irritabilityHistoryMapper
     */
    @Autowired
    private IrritabilityHistoryMapper irritabilityHistoryMapper;
    /**
     * 注入livingHistoryMapper
     */
    @Autowired
    private LivingHistoryMapper livingHistoryMapper;
    /**
     * 注入operationHistoryMapper
     */
    @Autowired
    private OperationHistoryMapper operationHistoryMapper;
    /**
     * 注入pastHistoryMapper
     */
    @Autowired
    private PastHistoryMapper pastHistoryMapper;
    /**
     * 注入patientMessageMapper
     */
    @Autowired
    private OperationTypeMapper operationTypeMapper;
    /**
     * 注入imageInfoMapper
     */
    @Autowired
    private ImageInfoMapper imageInfoMapper;

    /**
     * 将创建MDT中的病人病史信息存入数据库中
     * @param createMDTVoHistory 前台传入的总数据
     * @param mdt                // 创建完MDT的数据
     * @return ClincialResult
     */
    @Transactional
    @Override
    public ClincialResult insertIllnessHistory(CreateMDTVo createMDTVoHistory, MDT mdt, String mdtImageid) {
        System.out.println("****************************************新增病例史***************************************************");
        System.out.println("大哥传过来的mdtImageid的数据----->:" + mdtImageid);
        System.out.println("大哥哥传过来的数据----->:" + createMDTVoHistory.toString());
        System.out.println("创建成功的MDT的数据----->:" + mdt.toString());
        //insert现代史 补全现代病例史
        HistoryOfPresentIllness historyOfPresentIllness = new HistoryOfPresentIllness();
        historyOfPresentIllness.setName(createMDTVoHistory.getName()); // 拼接病人的名字
        historyOfPresentIllness.setCardinal_symptom(createMDTVoHistory.getMainSymptoms());  // 主要症状
        historyOfPresentIllness.setConcomitant_symptom(createMDTVoHistory.getSymptoms());  // 伴随症状
        historyOfPresentIllness.setOccurrence_time(createMDTVoHistory.getEpoch());  // 出现时间
        System.out.println("肿瘤分级----->:" + createMDTVoHistory.getTumor());
        historyOfPresentIllness.setTumour_classify(createMDTVoHistory.getTumor()); // 肿瘤分级
        historyOfPresentIllness.setPresent_situation(createMDTVoHistory.getAggravate()); // 现在情况
        historyOfPresentIllness.setTreatment_situation(createMDTVoHistory.getSituation()); // 治疗情况
        historyOfPresentIllness.setMdt_id(mdt.getId());  // 补全mdt_id
        // 向HISTORY_OF_PRESENT_ILLNESS表中加入数据
        historyOfPresentIllnessMapper.insertSelective(historyOfPresentIllness);

        //手术史  补全手术史  ----->这里面比较复杂
        System.out.println("MedHistory的数据格式----->:" + createMDTVoHistory.getMedHistory());
        JSONArray operation = JSONArray.fromObject(createMDTVoHistory.getMedHistory());
        OperationHistory operationHistory = null;
        Integer id = 0;
        //有手术类型
        if (operation.size() != 0) {

            OperationType operationType = null;
            // 在这里处理手术类型的判断 -----> 1 有手术类型, 就添加到表中去
            for (int k = 0; k < operation.size(); k++) {
                operationHistory = new OperationHistory();
                // 加入病史表中的id-----> 在这里生成不重复的ID
                //id = IDUtils.genShortItemId();
                //operationHistory.setId(id);
                // 手术史之前的医院
                if (operation.getJSONObject(k).getInt("isHospital") == 2) {
                    // 2 填写外院的名字
                    operationHistory.setHospital_name(operation.getJSONObject(k).getString("hospitalName"));
                } else {
                    // 1 填写本院的名字
                    operationHistory.setHospital_name("华锐立远开发部!");
                }
                operationHistory.setName(createMDTVoHistory.getName());
                // boolean time = operation.getJSONObject(k).containsKey("time");
                if(operation.getJSONObject(k).containsKey("time")){
                    operationHistory.setOperation_time(operation.getJSONObject(k).getString("time"));
                }else{
                    operationHistory.setOperation_time("");
                }

                // 看是否有手术状态
                operationHistory.setOperation_status(operation.getJSONObject(k).getInt("isOperation"));  //1代表有手术，2代表无手术

                // 检查结论及用药情况
                operationHistory.setMedication_situation(operation.getJSONObject(k).getString("medicationStatus"));
                // 加入该MDT的相关信息
                operationHistory.setMdt_id(mdt.getId());

                // 将数据插入到OPERATION_HISTORY 进行更新
                operationHistoryMapper.insertSelective(operationHistory);

                // 获取自动递增的主键id
                id = operationHistory.getId();

               // if (operation.getJSONObject(k).getInt("isOperation") == 1) {
                    // 有手术请更新另一张表
                    operationType = new OperationType();
                    operationType.setOperation_history_id(id);
                    for (int i = 0; i < operation.getJSONObject(k).getJSONArray("type").size(); i++) {
                        // 判断是否是手术类型
                        //operationType.setIs_operation_type(0); // 0 不是手术
                        if ("手术".equals(operation.getJSONObject(k).getJSONArray("type").get(i))) {
                            operationType.setIs_operation_type(1); // 1 是手术
                        }
                        if ("化疗".equals(operation.getJSONObject(k).getJSONArray("type").get(i))) {
                            operationType.setIs_chemotherapy_type(1);
                        }
                        if ("放疗".equals(operation.getJSONObject(k).getJSONArray("type").get(i))) {
                            operationType.setIs_radiotherapy_type(1);
                        }
                        if ("生物治疗".equals(operation.getJSONObject(k).getJSONArray("type").get(i))) {
                            operationType.setIs_biological_therapy(1);
                        }
                    }
                    if (operation.getJSONObject(k).getBoolean("flagType")) {
                        operationType.setOther_therapy(operation.getJSONObject(k).getString("text"));
                    }
                    operationType.setOperation_briefly(operation.getJSONObject(k).getString("briefly"));
                    System.out.println("需要插入数据库种的----->:" + operationType.toString());
                    operationTypeMapper.insertSelective(operationType);

            }
        }

        // 补全既往史
        PastHistory pastHistory = new PastHistory();
        pastHistory.setMdt_id(mdt.getId());
        pastHistory.setName(createMDTVoHistory.getName());
        pastHistory.setPast_history(createMDTVoHistory.getMedicalHistory());
        // 判断手术类型
        JSONArray medicalHistoryArray = JSONArray.fromObject(createMDTVoHistory.getMedicalHistory());
        for (int i = 0; i < medicalHistoryArray.size(); i++) {
            // 判断是否是手术类型
            //pastHistory.setIs_hava_hypertension(0); // 0 不是手术
            if ("高血压".equals(medicalHistoryArray.getString(i))) {
                pastHistory.setIs_hava_hypertension(1); // 1 是手术
            }
            if ("心脏病".equals(medicalHistoryArray.getString(i))) {
                pastHistory.setIs_hava_heart_disease(1);
            }
            if ("糖尿病".equals(medicalHistoryArray.getString(i))) {
                pastHistory.setIs_hava_diabetes(1);
            }
            if ("结核".equals(medicalHistoryArray.getString(i))) {
                pastHistory.setIs_hava_tuberculosis(1);
            }
            if ("肝炎".equals(medicalHistoryArray.getString(i))) {
                pastHistory.setIs_hava_hepatitis(1);
            }
        }
        pastHistory.setOther_past_history(createMDTVoHistory.getMedicalHistoryText());
        // 将数据插入到PAST_HISTORY 进行更新
        pastHistoryMapper.insertSelective(pastHistory);

        // 补全家族史
        FamilyHistory familyHistory = new FamilyHistory();
        familyHistory.setMdt_id(mdt.getId());
        familyHistory.setName(createMDTVoHistory.getName());
        familyHistory.setFamily_disease_history(createMDTVoHistory.getFaHistory());
        familyHistoryMapper.insertSelective(familyHistory);

        // 补全过敏史
        IrritabilityHistory irritabilityHistory = new IrritabilityHistory();
        irritabilityHistory.setName(createMDTVoHistory.getName());
        irritabilityHistory.setMdt_id(mdt.getId());
        irritabilityHistory.setIrritability_history(createMDTVoHistory.getAllergy());
        irritabilityHistoryMapper.insertSelective(irritabilityHistory);

        // 补全生活史
        LivingHistory livingHistory = new LivingHistory();
        livingHistory.setName(createMDTVoHistory.getName());
        livingHistory.setMdt_id(mdt.getId());
        if (createMDTVoHistory.getDrinking().equals("1") || createMDTVoHistory.getDrinking().equals("2") ||
                createMDTVoHistory.getDrinking().equals("3") || createMDTVoHistory.getDrinking().equals("4")) {
            livingHistory.setDrinking_history(Integer.parseInt(createMDTVoHistory.getDrinking())-1);
        }
        if (createMDTVoHistory.getSmoking().equals("1") || createMDTVoHistory.getSmoking().equals("2") ||
                createMDTVoHistory.getSmoking().equals("3") || createMDTVoHistory.getSmoking().equals("4")) {
            livingHistory.setSmoking_history(Integer.parseInt(createMDTVoHistory.getSmoking())-1);
        }
        livingHistoryMapper.insertSelective(livingHistory);

        // 更新IMAGE_INFO表中的mdt_id
        JSONArray jsonArray = JSONArray.fromObject(mdtImageid);
        ImageInfo imageInfo = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println("id是否显示出来----->:" + jsonArray.getString(i));
            imageInfo = new ImageInfo();
            // Integer.parseInt(jsonArray.getString(i)) 字符串如果不是数字, 会抛异常, 请判断字符串是否是数字 -----> NumberUtils
            // org.apache.commons.lang.math.NumberUtils包下的NumberUtils工具类, 可以判断字符串和数字的关系
            // NumberUtils.isDigits("");和NumberUtils.isNumber("");都可以用-----> 检查字符串是否是只包含数字字符，Null和空将会返回false;
            if (NumberUtils.isNumber(jsonArray.getString(i))) {
                imageInfo.setImage_Id(Integer.parseInt(jsonArray.getString(i)));
                imageInfo.setIMdtId(mdt.getId());
                imageInfoMapper.updateByPrimaryKeySelective(imageInfo);
            }

        }

        // 添加病人信息到表patient_message中
        PatientMessage patientMessage = new PatientMessage();
        patientMessage.setPatientName(createMDTVoHistory.getName());  // 加入病人的名字
        if(NumberUtils.isNumber(createMDTVoHistory.getAge())) {
            patientMessage.setAge(Integer.parseInt(createMDTVoHistory.getAge()));  // 加入病人的年龄
        }else{
            patientMessage.setAge(0);
        }
        patientMessage.setSex(createMDTVoHistory.getSex());  // 加入病人的性别
        patientMessage.setPhone(createMDTVoHistory.getPhone());  // 加入病人的手机号和联系方式
        patientMessage.setDiseaseArea(createMDTVoHistory.getWard());  // 加入病人的病区
        if(NumberUtils.isNumber(createMDTVoHistory.getSick())) {  // 加入病号
            patientMessage.setDiseaseNumber(Integer.parseInt(createMDTVoHistory.getSick()));
        }else{
            patientMessage.setDiseaseNumber(0);
        }
        if(NumberUtils.isNumber(createMDTVoHistory.getHospital())) {  // 加入住院号
            patientMessage.setHospitalNumber(Integer.parseInt(createMDTVoHistory.getHospital()));
        }else{
            patientMessage.setHospitalNumber(0);
        }
        patientMessage.setDoctorMain(createMDTVoHistory.getPhysician());  // 加入主管医生
        patientMessage.setMdtId(mdt.getId()); // 加入mdt的id
        int flag = patientMessageMapper.insertSelective(patientMessage);
        // 新增失败
        if(flag == 0){
            return ClincialResult.build(500, "add病例史失败!", null);
        }
        // 新增成功
        return ClincialResult.build(200, "add病例史成功!", null);
    }

}
