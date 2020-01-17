package cn.knowimage.mapper;

import cn.knowimage.pojo.vo.SubmitTreament;
import cn.knowimage.pojo.instance.Treatment;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SubmitTreatmentMapper extends Mapper<Treatment> {

    @Select("SELECT distinct t.name,t.id  FROM TREATMENT_TYPE t,(SELECT treatment_id FROM TUMOR_TYPE_TREATMENT WHERE tumor_id in (SELECT TUMOR_ID FROM mdt_dis WHERE MDT_ID=#{mdtId}))r WHERE r.treatment_id=t.id")
    List<SubmitTreament> selectTreatmentWay(Integer mdtId);
    //判断数据库个人决策是否已经存在
    @Select("SELECT COUNT(creator) FROM PERSONAL_DECISION where mdt_id=#{mdtId} and creator=#{creator}")
    Integer selectPersioncount(Integer mdtId, Integer creator);
}
