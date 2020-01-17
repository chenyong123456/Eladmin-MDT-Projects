package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.GroupDecision;
import cn.knowimage.pojo.vo.SumVote;
import cn.knowimage.pojo.vo.Votes;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GroupTreatmentMapper extends Mapper<GroupDecision> {
    @Select("SELECT count(*) as con FROM mdt_tnm where IF_ACCEPT=3 and MDT_ID=#{mdtId}")
    Integer selectMdtcount(Integer mdtId);

    @Select("SELECT count(DISTINCT(creator)) from PERSONAL_DECISION where MDT_ID=#{mdtId}")
    Integer selectAcceptcount(Integer mdtId);

    @Select("SELECT d.weightings,p.priority FROM DEPARTMENT_WEIGHT_SETTING d,EL_USER e,PERSONAL_DECISION p " +
            "where d.tumor_id=p.tumor_id and d.department_id=e.job_id AND e.id=p.creator " +
            "and p.mdt_id=#{mdtId} AND p.creator=#{creator} AND p.treatment_id=#{treatmentId}")
    List<Votes> selectWeightings(Integer mdtId, Integer creator, Integer treatmentId);

    //判断是否做过群组决策
    @Select("select id from GROUP_DECISION WHERE mdt_id=#{mdtId} AND creator=#{creator}")
    List<String> selectIdList(Integer mdtId, Integer creator);

    //查询出来总票数和治疗方案
    @Select("SELECT  SUM(g.votes) as votes,t.name from GROUP_DECISION g,TREATMENT_TYPE t " +
            " WHERE t.id=g.treatment_id AND g.mdt_id=#{mdt} GROUP BY g.treatment_id")
    List<SumVote> selectNameVote(Integer mdtId);

    //查询一元投票还是二元投票
    //@Select("")
}
