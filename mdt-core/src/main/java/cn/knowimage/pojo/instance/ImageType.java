package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "IMAGE_TYPE")
public class ImageType {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "type_Id")
    private Integer type_Id;

    @Column(name = "type_Name")
    private String typeName; //type_name


}
