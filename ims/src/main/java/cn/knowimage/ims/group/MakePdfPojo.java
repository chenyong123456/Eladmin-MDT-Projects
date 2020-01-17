package cn.knowimage.ims.group;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 前台传过来生成PDF文件报告的数据
 * @author yong.Mr
 * @version 1.0
 * @data 2019-12-03
 */
@Data
public class MakePdfPojo {

    // 病人的名字
    private String name;

    // 姓名发
    private String name_eng;

    // 地址
    private String address;

    private String contact;

    // 同一标题
    private String agreeTitle;

    // 签名
    private String signName;

    // 报告标题
    private String reportTitle;

    // 签名2
    private String signName2;

    // 横幅
    private MultipartFile fileList[];

    // 无字符横幅
    private MultipartFile imgList[];


}
