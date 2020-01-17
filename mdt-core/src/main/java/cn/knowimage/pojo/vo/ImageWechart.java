package cn.knowimage.pojo.vo;

import lombok.Data;

@Data
public class ImageWechart {
    private String image_Details;
    private String i_url;
    private String create_time;
    //@Column(name = "type_Id")
    private String type_Id;
    private String type_Name;
    private String i_thumbnail_url;
}
