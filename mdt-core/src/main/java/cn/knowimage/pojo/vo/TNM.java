package cn.knowimage.pojo.vo;

import lombok.Data;

@Data

public class TNM {

    //提交TNM的KEY值
    private String tnm_key;

    //提交TNM后对应的value
    private String value;

    //提交TNM的用户名称
    private String username;

    //疾病名称
    private String disease;

}
