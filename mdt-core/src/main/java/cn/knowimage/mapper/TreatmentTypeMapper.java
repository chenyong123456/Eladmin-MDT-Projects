package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.TreatmentType;
import cn.knowimage.pojo.vo.TreatmentPojo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface TreatmentTypeMapper extends Mapper<TreatmentType> {
  /**
   * 批量新增治疗类型并且返回主键id
   * @param type 治疗类型
   * @return
   */
  @Insert("<script>insert into TREATMENT_TYPE (name) values " +
    "<foreach collection='list' item='c' separator=','>(#{c.name})</foreach></script>")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int addTreatment(@Param("list") List<TreatmentType> type);
  /**
   * 查询治疗方式&治疗类型
   * @return
   */
  @Select("select t.name as typeName,t.id as typeId,p.id as treatmentId,p.name as planName from TREATMENT_TYPE t left join TREATMENT_PLAN p on t.id = p.treatment_type_id")
  List<TreatmentPojo> getTreatment();


//  @Select("select * from TREATMENT_TYPE")
//  List<TreatmentType> queryAllTreatment();
}
