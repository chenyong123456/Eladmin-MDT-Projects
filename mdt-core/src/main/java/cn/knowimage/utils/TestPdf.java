package cn.knowimage.utils;

import cn.knowimage.ims.vo.MakePdfPojo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * 根据相应的数据生成相应的PDF文件
 *
 * @author yong.Mr
 * @version 1.0
 * @data 2019-12-04
 */
public class TestPdf {
    private static Font headfont;// 设置字体大小
    private static Font keyfont;// 设置字体大小
    private static Font textfont;// 设置字体大小

    static {
        BaseFont bfChinese;
        try {
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);//支持中文

            headfont = new Font(bfChinese, 13, Font.BOLD);// 设置字体大小
            keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
            textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String writeCharpter(MakePdfPojo makePdf) throws Exception {
//新建document对象  第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 50, 50, 30, 30);
//建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。

        String file = "C:\\Users\\wh123\\Desktop\\test.pdf";

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//打开文件
        document.open();
        //加列表
        PdfPTable table = new PdfPTable(5);
        //// 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        // 设置表格的宽度
        table.setTotalWidth(500);
        // 也可以每列分别设置宽度
//        table.setTotalWidth(new float[] { 200, 100, 100, 100 });
        // 锁住宽度
//        table.setLockedWidth(true);
        // 设置表格上面空白宽度
        table.setSpacingBefore(10f);
        // 设置表格下面空白宽度
        table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);

        // 在表格添加图片
     /*   Image cellimg = Image.getInstance("C:\\Users\\wh123\\Desktop\\002.png");
        PdfPCell cell = new PdfPCell(cellimg, true);
//        PdfPCell cell = new PdfPCell(new Phrase("头像",keyfont));
        cell.setRowspan(20);
        // 设置距左边的距离
        cell.setPaddingLeft(10);
        // 设置无边框
        cell.setBorder(Rectangle.NO_BORDER);
        // 设置高度
        cell.setFixedHeight(10);
        // 设置内容水平居中显示
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);*/


        PdfPCell pCell2 = new PdfPCell();
        pCell2.setPhrase(new Phrase(" ", keyfont));
        pCell2.setBorder(Rectangle.NO_BORDER);
        // 设置占用列数
        pCell2.setColspan(2);
        table.addCell(pCell2);
       /* PdfPCell pCell3 = new PdfPCell();
        pCell3.setBorder(Rectangle.NO_BORDER);
        pCell3.setPhrase(new Phrase("公司、职位", keyfont));
        pCell3.setColspan(3);
        pCell3.setPaddingLeft(10);
        pCell3.setFixedHeight(20);
        // 设置内容水平居中显示
        pCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        // 设置垂直居中
        pCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell3);*/

        PdfPCell pCell6 = new PdfPCell();
        pCell6.setBorder(Rectangle.NO_BORDER);
        pCell6.setPhrase(new Phrase("n年工作经历/学历/28岁", headfont));
        pCell6.setColspan(3);
        pCell6.setPaddingLeft(5);
        pCell6.setFixedHeight(20);
        // 设置内容水平居中显示
        pCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        // 设置垂直居中
        pCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell6);



        PdfPCell pCell4 = new PdfPCell();
        pCell4.setBorder(Rectangle.NO_BORDER);
        pCell4.setPhrase(new Phrase("1555555555555:"));
        pCell2.setColspan(3);
        pCell4.setPaddingLeft(20);
        pCell4.setFixedHeight(50);
        // 设置内容水平居中显示
        pCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        // 设置垂直居中
        pCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell4);

        PdfPCell pCell5 = new PdfPCell();
        pCell5.setBorder(Rectangle.NO_BORDER);
        pCell5.setPhrase(new Phrase("1555555@qq.com"));
        pCell5.setColspan(2);
        pCell5.setPaddingLeft(20);
        pCell5.setFixedHeight(20);
        // 设置内容水平居中显示
        pCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        pCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell5);

        PdfPCell pCell7 = new PdfPCell();
        pCell7.setBorder(Rectangle.NO_BORDER);
        pCell7.setPhrase(new Phrase("自我描述", keyfont));
        pCell7.setColspan(4);
        pCell7.setPaddingLeft(10);
        pCell7.setFixedHeight(20);
        pCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell7);

        PdfPCell pCell8 = new PdfPCell();
        pCell8.setBorder(Rectangle.NO_BORDER);
        pCell8.setPhrase(new Phrase("自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述", keyfont));
        pCell8.setColspan(4);
        pCell8.setPaddingLeft(10);
        pCell8.setFixedHeight(20);
        pCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pCell8);

        PdfPCell workExp = new PdfPCell();
        workExp.setBorder(Rectangle.NO_BORDER);
        workExp.setPhrase(new Phrase("工作经历", keyfont));
        workExp.setColspan(4);
        workExp.setPaddingLeft(10);
        workExp.setFixedHeight(20);
        workExp.setHorizontalAlignment(Element.ALIGN_LEFT);
        workExp.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(workExp);


        Image corp = Image.getInstance("C:\\Users\\wh123\\Desktop\\002.png");
        PdfPCell corpImg = new PdfPCell(corp, true);
        corpImg.setRowspan(2);
        corpImg.setPaddingLeft(10);
        corpImg.setBorder(Rectangle.NO_BORDER);
        corpImg.setFixedHeight(20);
        corpImg.setHorizontalAlignment(Element.ALIGN_CENTER);
        corpImg.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(corpImg);

        PdfPCell corpName = new PdfPCell();
        corpName.setBorder(Rectangle.NO_BORDER);
        corpName.setPhrase(new Phrase("公司名称", keyfont));
        corpName.setColspan(2);
        corpName.setPaddingLeft(10);
        corpName.setFixedHeight(20);
        corpName.setHorizontalAlignment(Element.ALIGN_LEFT);
        corpName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(corpName);

        PdfPCell corpTime = new PdfPCell();
        corpTime.setBorder(Rectangle.NO_BORDER);
        corpTime.setPhrase(new Phrase("时间", keyfont));
        corpTime.setColspan(1);
        corpTime.setPaddingLeft(10);
        corpTime.setFixedHeight(20);
        corpTime.setHorizontalAlignment(Element.ALIGN_LEFT);
        corpTime.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(corpTime);


        PdfPCell workName = new PdfPCell();
        workName.setBorder(Rectangle.NO_BORDER);
        workName.setPhrase(new Phrase("任职职务", keyfont));
        workName.setColspan(3);
        workName.setPaddingLeft(10);
        workName.setFixedHeight(20);
        workName.setHorizontalAlignment(Element.ALIGN_LEFT);
        workName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(workName);

        PdfPCell jobExp = new PdfPCell();
        jobExp.setBorder(Rectangle.NO_BORDER);
        jobExp.setPhrase(new Phrase("项目经历", keyfont));
        jobExp.setColspan(4);
        jobExp.setPaddingLeft(10);
        jobExp.setFixedHeight(20);
        jobExp.setHorizontalAlignment(Element.ALIGN_LEFT);
        jobExp.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(jobExp);


        PdfPCell jobName = new PdfPCell();
        jobName.setBorder(Rectangle.NO_BORDER);
        jobName.setPhrase(new Phrase("项目名称", keyfont));
        jobName.setColspan(2);
        jobName.setPaddingLeft(10);
        jobName.setFixedHeight(20);
        jobName.setHorizontalAlignment(Element.ALIGN_LEFT);
        jobName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(jobName);

        PdfPCell jobTime = new PdfPCell();
        jobTime.setBorder(Rectangle.NO_BORDER);
        jobTime.setPhrase(new Phrase("项目时间", keyfont));
        jobTime.setColspan(2);
        jobTime.setPaddingLeft(10);
        jobTime.setFixedHeight(20);
        jobTime.setHorizontalAlignment(Element.ALIGN_RIGHT);
        jobTime.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(jobTime);

        PdfPCell jobDescr = new PdfPCell();
        jobDescr.setBorder(Rectangle.NO_BORDER);
        jobDescr.setPhrase(new Phrase("自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述" +
                "自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述自我描述", keyfont));
        jobDescr.setColspan(4);
        jobDescr.setPaddingLeft(10);
        jobDescr.setFixedHeight(20);
        jobDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
        jobDescr.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(jobDescr);

        PdfPCell eduExp = new PdfPCell();
        eduExp.setBorder(Rectangle.NO_BORDER);
        eduExp.setPhrase(new Phrase("教育经历", keyfont));
        eduExp.setColspan(4);
        eduExp.setPaddingLeft(10);
        eduExp.setFixedHeight(20);
        eduExp.setHorizontalAlignment(Element.ALIGN_LEFT);
        eduExp.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(eduExp);


        Image edu = Image.getInstance("C:\\Users\\wh123\\Desktop\\002.png");
        PdfPCell eduImg = new PdfPCell(edu, true);
        eduImg.setRowspan(2);
        eduImg.setPaddingLeft(10);
        eduImg.setBorder(Rectangle.NO_BORDER);
        eduImg.setFixedHeight(20);
        eduImg.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduImg.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(eduImg);

        PdfPCell eduName = new PdfPCell();
        eduName.setBorder(Rectangle.NO_BORDER);
        eduName.setPhrase(new Phrase("教育机构名称", keyfont));
        eduName.setColspan(2);
        eduName.setPaddingLeft(10);
        eduName.setFixedHeight(20);
        eduName.setHorizontalAlignment(Element.ALIGN_LEFT);
        eduName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(eduName);

        PdfPCell eduTime = new PdfPCell();
        eduTime.setBorder(Rectangle.NO_BORDER);
        eduTime.setPhrase(new Phrase("时间", keyfont));
        eduName.setColspan(1);
        eduTime.setPaddingLeft(10);
        eduTime.setFixedHeight(20);
        eduTime.setHorizontalAlignment(Element.ALIGN_LEFT);
        eduTime.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(eduTime);


        PdfPCell eduDir = new PdfPCell();
        eduDir.setBorder(Rectangle.NO_BORDER);
        eduDir.setPhrase(new Phrase("学习方向", keyfont));
        eduDir.setColspan(4);
        eduDir.setPaddingLeft(10);
        eduDir.setFixedHeight(20);
        eduDir.setHorizontalAlignment(Element.ALIGN_LEFT);
        eduDir.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(eduDir);

        document.add(table);

        //关闭文档
        document.close();
        return file;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("begin");
        MakePdfPojo makePdf = new MakePdfPojo();
        TestPdf ppt = new TestPdf();
        ppt.writeCharpter(makePdf);
        System.out.println("end");

    }

}
