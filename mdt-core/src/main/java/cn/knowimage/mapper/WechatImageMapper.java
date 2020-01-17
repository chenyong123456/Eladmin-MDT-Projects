package cn.knowimage.mapper;

import cn.knowimage.pojo.vo.ImageWechart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WechatImageMapper {
    //查询并排序
    @Select("select i.image_Details,i.i_url,i.i_thumbnail_url,i.create_time,t.type_Name FROM " +
            "IMAGE_INFO i,IMAGE_TYPE t " +
            "where i.i_type_Id=t.type_Id and i_MDT_Id=#{i_MDT_Id} and type_Id=#{type_Id} ORDER BY IFNULL(i.create_time,0) DESC")
    List<ImageWechart> selectWechat(Integer i_MDT_Id, Integer type_Id);

    //排序



}
