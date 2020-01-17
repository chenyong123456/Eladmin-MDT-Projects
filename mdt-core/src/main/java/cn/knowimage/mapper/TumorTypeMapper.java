package cn.knowimage.mapper;

import cn.knowimage.pojo.instance.TumorType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 肿瘤类型mapper
 * @author Liang Yuxuan
 * @date 2019/12/26
 */
@org.apache.ibatis.annotations.Mapper
public interface TumorTypeMapper extends Mapper<TumorType> {
  @Select("select * from TUMOR_TYPE where pid <>0 ")
  List<TumorType> getAllTumorType();
  @Insert("insert into TUMOR_TYPE(name,pid,sort,creator,create_time,modifier,modify_time) values(#{name},1,16,#{creator},#{createTime},null,null)")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  int addTumorType(TumorType tumorType);





}
