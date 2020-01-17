package cn.knowimage.service.impl;

import cn.knowimage.exception.NullException;
import cn.knowimage.pojo.instance.ImageInfo;
import cn.knowimage.pojo.instance.ImageType;
import cn.knowimage.mapper.ImageMapper;
import cn.knowimage.mapper.ImageTypeMapper;
import cn.knowimage.service.ImageService;
import cn.knowimage.utils.ImageThumbnailUtil;
import cn.knowimage.utils.ClincialResult;
import cn.knowimage.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UploadServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageTypeMapper imageTypeMapper;

    //HttpServletRequest req=(HttpServletRequest)request;
    //创建文件上传的方法，返回状态码
    public ClincialResult upimage(MultipartFile file) throws IOException {
        if (file.isEmpty()){
            throw new NullException("请传入你要上传的文件！！！");
        } else {
        InputStream inputStream = null;
        String imageId;
        try {
            //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String OriginalfileName = file.getOriginalFilename();//获取文件名
            String suffixName = OriginalfileName.substring(OriginalfileName.lastIndexOf("."));
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            System.out.println(fileName);
            inputStream = file.getInputStream();
            //插入部分数据进入数据库，并更新信息
            ImageInfo info = new ImageInfo();
            info.setImageName(fileName);
            imageMapper.insertSelective(info);
            imageId = imageMapper.selectimageId(fileName);
            //更新文件名为时间戳+图片id
            String lastfileName = fileName + imageId + suffixName;
            System.out.println(lastfileName);
            //更新imageInfo中的信息
            ImageInfo Info2 = new ImageInfo();
            Info2.setImageId(imageId);
            Info2.setImageName(lastfileName);
            Info2.setI_url("https://cy.knowimage.cn:7022/mdtImage/" + fileName + imageId + suffixName);
            Info2.setI_thumbnail_url("https://cy.knowimage.cn:7022/mdtImage/" + "min"+fileName + imageId + suffixName);
            //调用通用mapper中的根据主键更新的方法
            imageMapper.updateByPrimaryKey(Info2);
            Boolean flag = FtpUtil.uploadFile("192.168.50.4", 21, "ftpuser", "ftpuser", "/image", "/MDT", lastfileName, inputStream);
            String prevfix="min"+lastfileName;
            //对file产生缩略图
            ImageThumbnailUtil.thumbnailImage(file,200,150,prevfix,true);
            if (flag) {
                System.out.println("成功了!");

            } else {
                System.out.println("失败了!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ClincialResult.build(404, "上传失败");
        } finally {
            inputStream.close();
        }
        return ClincialResult.build(200, "图片上传成功", imageId);
    }
    }
    @Override
    //更新图片详情
    public ClincialResult updateImage(String image_details, String image_type,String time, String imageId) {
        //查询typeId
        Example example1 = new Example(ImageType.class);
        Example.Criteria criteria = example1.createCriteria();
        criteria.andEqualTo("typeName", image_type);
        ImageType type = imageTypeMapper.selectOneByExample(example1);
        Integer i_type_Id = type.getType_Id();
        System.out.println("类型id-----》" + i_type_Id);
        //更新图片中的信息
        ImageInfo info = new ImageInfo();
        info.setImageId(imageId);
        info.setImage_Details(image_details);
        info.setI_type_Id(i_type_Id);
        info.setCreateTime(time);
        imageMapper.updateByPrimaryKeySelective(info);
        return ClincialResult.build(200, "信息编辑成功");
    }
    @Override
    //删除数据库以及服务器上的数据
    public void deleteImage(String imageId) {
        imageMapper.deleteImage(imageId);
        String fileName = imageMapper.selectFilename(imageId);
        //删除服务器上的数据
        FtpUtil.deleteFile("192.168.50.4", 21, "ftpuser", "ftpuser", "/image/MDT", fileName);
    }
}