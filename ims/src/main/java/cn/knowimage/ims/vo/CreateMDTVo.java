package cn.knowimage.ims.vo;

import lombok.Data;

/**
 * 朱涛接收大哥前台传过来的数据-----创建MDT的数据
 * @author yong.Mr
 * @date 2019-12-18
 * @version 1.0
 */
@Data
public class CreateMDTVo {

    private Integer uid;  //登录人的ID

    private String mdtName; //创建MDT的名称

    private String create_name;

    private String name;  //病人名称

    private String age;

    private String sex;

    private String phone;

    private Boolean is_repeat;

    private String ward;  // 病区

    private String sick;  // 病号

    private String hospital;  // 住院号

    private String physician;  // 主管医师

    private String introduction;

    private String PreviousTreatment;

    private Integer goal; // 创建MDT的目的

    private String goal2; // 其他目的

    private String isPays; // 病人是否自费

    private String[] type;

    private String region;

    private String time;

    private String time2;

    private String location;

    private Integer tumorType;  // MDT所属的病情

    private String way;  // MDT的讨论方式

    // 现病史主要变量
    // 主要症状
    private String mainSymptoms;

    // 肿瘤分级
    private String tumor;

    // 伴随症状
    private String symptoms;

    // 出现时间
    private String epoch;

    // 缓解/加重情况
    private String aggravate;

    // 发病后用药治疗情况
    private String situation;

    private String medHistory;  // 手术史

    // 既往病史
    private String medicalHistory;

    // 既往病史其他
    private String medicalHistoryText;

    // 家族史
    private String faHistory;

    // 过敏史
    private String allergy;

    // 饮酒史
    private String drinking;

    // 吸烟史
    private String smoking;

    /**
     *  // 现病史
     *         flag: false,
     *         mainSymptoms: "", // 主要症状
     *         symptoms: "", // 伴随症状
     *         epoch: "", //出现时间
     *         aggravate: "", // 缓解/加重情况
     *         situation: "", // 发病后用药治疗情况
     *         medHistory: [
     *           // {
     *           //   time: "", //手术时间
     *           //   type: [], //手术类型
     *           //   medicalHistory: "", //既往病史
     *           //   medicationStatus: "" //既往用药情况
     *           // }
     *         ], //既往史
     *         faHistory: "", // 家族史
     *         allergy: "", // 过敏史
     *         drinking: "无", //饮酒史
     *         smoking: "无", //吸烟史
     */

}
