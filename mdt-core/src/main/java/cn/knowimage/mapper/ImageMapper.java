package cn.knowimage.mapper;



import cn.knowimage.pojo.instance.ImageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface ImageMapper extends Mapper<ImageInfo>{
    @Select("select distinct i_type_Id FROM IMAGE_INFO where i_MDT_Id=#{i_MDT_Id}")
    List<ImageInfo> selectTypeIdByMDTId(Integer mdtId);

    @Delete("delete FROM IMAGE_INFO where image_Id=#{image_Id}")
    void deleteImage(String image_Id);

    @Select("select image_Name FROM IMAGE_INFO where image_Id=#{imgae_Id}")
    String selectFilename(String image_Id);

    @Select("select image_Id FROM IMAGE_INFO where image_Name=#{image_Name}")
    String selectimageId(String fileName);

}
