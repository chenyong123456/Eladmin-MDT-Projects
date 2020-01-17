package cn.knowimage.mapper;

import cn.knowimage.pojo.vo.TNM;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TNMMapper {

    @Select("select id from tnm where tnm_key = #{key} and disease = #{disease}")
    Integer findByTNM(String key, String disease);

    @Select("select tnm.value,tnm.tnm_key,EL_USER.username,tnm.disease from tnm,EL_USER,mdt_tnm where mdt_tnm.mdt_id = #{id} and tnm.id = mdt_tnm.tnm_id and EL_USER.id = mdt_tnm.user_id")
    List<TNM> findTNMById(int id);

    @Update("update mdt_tnm set tnm_id = #{tnm_id} where mdt_id = #{mdt_id} and user_id = #{user_id}")
    void insertTNM(int mdt_id, int user_id, int tnm_id);

    @Select("select tree_id from TNM where tnm_key = #{key} and disease = #{disease}")
    Integer findHeadId(String key, String disease);

}
