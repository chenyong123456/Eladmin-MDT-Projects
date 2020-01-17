package cn.knowimage.mapper;


import cn.knowimage.pojo.vo.GroupWechatTreatment;
import cn.knowimage.pojo.instance.PersonalDecision;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AcceptTreatmentMapper extends Mapper<PersonalDecision> {
    @Select("SELECT tumor_id FROM mdt_dis where MDT_ID=#{mdtId}")
    Integer selectTumorByMdtId(Integer mdtId);

    //查询出治疗方式和id
    @Select("SELECT DISTINCT t.name,t.id FROM TREATMENT_TYPE t,PERSONAL_DECISION p WHERE p.treatment_id=t.id and mdt_id=#{mdt_id}")
    List<GroupWechatTreatment> selectGroupTreatment(Integer mdtId);

    //根据治疗类型id查询每个治疗方案医生的名字
    @Select("SELECT DISTINCT e.username FROM PERSONAL_DECISION p,EL_USER e where p.treatment_id=#{treatmentId} and e.id=p.creator and mdt_id=#{mdtId} and priority=#{priority}")
    List<String> selectDoctorName(Integer treatmentId, Integer mdtId, String priority);

    //根据MDTID查询所有医生的名字
    @Select("SELECT DISTINCT e.username,p.creator FROM PERSONAL_DECISION p,EL_USER e where p.mdt_id=#{mdtId} and e.id=p.creator")
    List<GroupWechatTreatment> selectDoctorNameByMdt(Integer treatmentId);


    //根据医生id查询不重复的备注信息
    @Select("SELECT DISTINCT remark FROM PERSONAL_DECISION where creator=#{creator} and mdt_id=#{mdtId}")
    String selectRemarkByMdtCreator(Integer mdtId, Integer creator);

    //更新数据插入优先级
    @Update("UPDATE PERSONAL_DECISION SET priority=#{priority} WHERE creator=#{creator} AND treatment_id=#{treatmentId}")
    void updatePriority(String priority, Integer creator, Integer treatmentId);




}
