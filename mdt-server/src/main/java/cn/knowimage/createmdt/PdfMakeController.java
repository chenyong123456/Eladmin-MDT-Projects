package cn.knowimage.createmdt;



import cn.knowimage.ims.vo.MakePdfPojo;
import cn.knowimage.utils.MakePdf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台自动生成PDF文件
 * @author yong.Mr
 * @version 1.0
 * @data 2019-12-03
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/pdf")
public class PdfMakeController {

    @RequestMapping("/make")
    public String makePdf(MakePdfPojo makePdf) {

        System.out.println("前台传过来的数据个格式------->:" + makePdf.toString());

        System.out.println("测试前台传过来的数据----->:" + makePdf.getName());

        try {
            MakePdf.createPdf(makePdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "https://cy.knowimage.cn:7022/pdf/test.pdf";
    }

}
