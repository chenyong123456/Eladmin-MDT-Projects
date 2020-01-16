package cn.knowimage.mdt.mapper;


import cn.knowimage.instance.Department;
import cn.knowimage.instance.MDT;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface MDTMapper extends Mapper<MDT>{
    /**
     * 大哥的分页
     * @param uid
     * @param dataNumber
     * @param page
     * @param queryName
     * @param queryConsultation
     * @return List<MDT>
     */
    @Select("select mdt.*,mdt_tnm.TNM_ID,mdt_tnm.IF_ACCEPT from mdt,mdt_tnm where mdt_tnm.user_id = #{uid} and mdt_tnm.mdt_id = mdt.id" +
            " and mdt.name LIKE '%${queryConsultation}%' and mdt.PATIENT_NAME LIKE '%${queryName}%'" +
            " limit ${(page-1)*dataNumber},${dataNumber}")
    List<MDT> getMDTListByUser(int uid, int dataNumber, int page, String queryName, String queryConsultation);

    @Select("select mdt.*,mdt_tnm.TNM_ID,mdt_tnm.IF_ACCEPT from mdt,mdt_tnm where mdt_tnm.user_id = #{uid} and mdt_tnm.mdt_id = mdt.id" +
            " and mdt.name LIKE '%${queryConsultation}%' and mdt.PATIENT_NAME LIKE '%${queryName}%'")
    List<MDT> getMDTList(int uid, String queryName, String queryConsultation);

    @Insert("insert into mdt_tnm(MDT_ID,USER_ID) value(#{mdt_id},#{uid})")
    void insertMDT(int uid, int mdt_id);

    @Select("select EL_JOB.* from EL_JOB,mdt_depart where mdt_depart.MDT_ID = #{mdtId} and EL_JOB.id = mdt_depart.HEAD_ID")
    Department getMDTDepart(int mdtId);

    /**
     * 给大哥的接口,该用户参入的MDT列表
     * @param uid
     * @return
     */
    @Select("select mdt.*,mdt_tnm.TNM_ID,mdt_tnm.IF_ACCEPT from mdt,mdt_tnm where mdt_tnm.user_id = #{uid} and mdt_tnm.mdt_id = mdt.id" +
            " AND mdt_tnm.IF_ACCEPT IN(1,2,3)")
    List<MDT> getMDTParticitionList(int uid);

    @Select("select mdt.ID,mdt.`NAME`,mdt_tnm.IF_ACCEPT,mdt.MEETING_LOCATION,mdt.PATIENT_NAME,mdt.PREDICT_TIME,mdt.GOAL,mdt_tnm.TNM_ID from mdt,mdt_tnm" +
            " where mdt_tnm.user_id = #{uid} and mdt_tnm.mdt_id = mdt.id AND mdt.CREATOR_ID = #{uid}")
    List<MDT> getOnselfCreateMdtList(int uid);



}
