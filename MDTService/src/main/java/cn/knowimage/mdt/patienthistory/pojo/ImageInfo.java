package cn.knowimage.mdt.patienthistory.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * image_info的图片服务器更新相应的mdt_id的信息
 * @author yong.Mr
 * @data 2019-12-23
 * @version 1.0
 */
@Data
@Table(name = "IMAGE_INFO")
public class ImageInfo {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "image_Id")
    private Integer image_Id;

    private String image_Name;

    private String image_Details;

    @Column(name = "i_MDT_Id")
    private Integer iMdtId;

    private String i_type_Id;

    private String i_url;

}
