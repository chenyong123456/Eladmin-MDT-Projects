package cn.knowimage.utils;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

//import com.ares.slf4j.test.Slf4jUtil;

public class ImageThumbnailUtil {
    /*static {
        Slf4jUtil.buildSlf4jUtil().loadSlf4j();
    }*/
    private Logger log = LoggerFactory.getLogger(getClass());

    private static String DEFAULT_PREVFIX = "thumb_";
    private static Boolean DEFAULT_FORCE = false;

    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     *
     * @param /imagePath 原图片路径
     * @param w          缩略图宽
     * @param h          缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force      是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static void thumbnailImage(MultipartFile imgFile, int w, int h, String prevfix, boolean force) {
        String p = imgFile.getOriginalFilename();
        System.out.println("是否获取文件名-----》：" + p);
        if (!imgFile.isEmpty()) {
            try {
                System.out.println("suffix后缀-----》：" + p.substring(p.lastIndexOf(".") + 1));
                String suffix = p.substring(p.lastIndexOf(".") + 1);
                System.out.println("target image's size, width:{}, height:{}." + w + h);
                File file = multipartToFile(imgFile, File.separator+"root");
                Image img = ImageIO.read(file);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            System.out.println("change image's height, width:{}, height:{}." + w + h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            System.out.println("change image's width, width:{}, height:{}." + w + h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
               // p = imgFile.getOriginalFilename();
                System.out.println("cscsac----->：" + p);
               // ImageIO.write(bi, suffix, new File("C:\\Users\\L\\Desktop\\" + p));
                ByteArrayOutputStream out=new ByteArrayOutputStream();
                ImageIO.write(bi, suffix,out);
                //byte[] bytes=out.toByteArray();
                InputStream input=new ByteArrayInputStream(out.toByteArray());
                String minfileName=prevfix;
                FtpUtil.uploadFile("192.168.50.4", 21, "ftpuser", "ftpuser", "/image", "/MDT", minfileName, input);
                File file1 = new File(File.separator+"root");

                System.out.println("要删除的图片名称-----》：" + file1.getName()) ;
                if(file1.delete()){
                    System.out.println("成功") ;
                }
            } catch (IOException e) {
                System.out.println("generate thumbnail image failed." + e);
            }
        } else {
            System.out.println("the image is not exist.");
        }
    }

    public static File multipartToFile(MultipartFile multipart, String imagePath) throws IOException {
        String osName = System.getProperty("os.name", "");
        System.out.println("当前操作系统为" + osName + "系统--------------------------");
        File file = new File(imagePath + File.separator + multipart.getName());
        file.setWritable(true, false);
        multipart.transferTo(file);

        return file;
    }

    public static MultipartFile fileToMultipartfile(String imagePath) throws Exception {
        File file = new File(imagePath);
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        System.out.println("file转multipartFile成功. {}" + multipartFile.getName());
        return multipartFile;
    }

    public static MultipartFile getMulFileByPath(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }


    public static FileItem createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void thumbnailImage(String imagePath1, int w, int h, String prevfix, boolean force) throws Exception {
        //File imgFile=new File(imagePath1);
        MultipartFile file2 = fileToMultipartfile(imagePath1);
        System.out.println("是否转换成功----->:" + file2.getOriginalFilename());
        thumbnailImage(file2, w, h, prevfix, force);
    }


    public void thumbnailImage(String imagePath, int w, int h, boolean force) throws Exception {
        thumbnailImage(imagePath, w, h, DEFAULT_PREVFIX, force);
    }

    public void thumbnailImage(String imagePath, int w, int h) throws Exception {
        thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
    }

    public static void main(String[] args) throws Exception {
        new ImageThumbnailUtil().thumbnailImage("C:\\Users\\L\\Desktop\\779334da99e40adb587d0ba715eca102.jpg", 100, 150, "min", false);
    }
}
