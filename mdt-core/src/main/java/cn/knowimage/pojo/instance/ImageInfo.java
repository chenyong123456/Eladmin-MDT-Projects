package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "IMAGE_INFO")
public class ImageInfo {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "image_Id")
    private String imageId;

    @Column(name = "image_Name")
    private String imageName;

    @Column(name = "image_Details")
    private String image_Details;

    //@Column(name = "i_MDT_Id")
    private long i_MDT_Id;
    @Column(name = "i_type_Id")

    private Integer i_type_Id;
    @Column(name = "i_url")

    private String i_url;

    @Column(name = "i_thumbnail_url")
    private String i_thumbnail_url;

    @Column(name = "create_time")
    private String createTime;
}
