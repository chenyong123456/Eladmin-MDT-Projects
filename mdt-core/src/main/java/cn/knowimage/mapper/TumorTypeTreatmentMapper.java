package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.TumorTypeTreatment;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 肿瘤类型对应的治疗方式mapper
 * @author Liang Yuxuan
 * @date 2019/12/26
 */
@org.apache.ibatis.annotations.Mapper
public interface TumorTypeTreatmentMapper extends Mapper<TumorTypeTreatment> {
  /**
   * 根据肿瘤类型查询对应的治疗方式
   * @param tumorId
   * @return
   */
//  @Select("select t.id as tumorId,t.name as tumorName,y.id as treatmentId,y.`name` as treatmentName from TUMOR_TYPE t \n" +
//    "inner join TUMOR_TYPE_TREATMENT m  on t.id = m.tumor_id  \n" +
//    "inner join TREATMENT_TYPE y on y.id = m.treatment_id\n" +
//    "where pid !=0 and t.id =#{tumorId}")
//  List<TumorTypeTreatmentPojo> queryTreatmentByTumor(String tumorId);

  /**
   * 根据肿瘤类型查询对应的治疗方式
   * @param tumorId 肿瘤id
   * @return List<Integer> 治疗方式数组
   */
  @Select("select treatment_id from TUMOR_TYPE_TREATMENT where tumor_id =#{tumorId}")
  List<Integer> queryTreatmentByTumor(Integer tumorId);


}
