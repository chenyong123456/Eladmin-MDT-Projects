package cn.knowimage.image;


import cn.knowimage.exception.NullException;
import cn.knowimage.mapper.ImageTypeMapper;
import cn.knowimage.service.impl.UploadServiceImpl;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/imageUpload")
public class UploadController {
    @Autowired
    private UploadServiceImpl uploadServicesImpl;
    @Autowired
    private ImageTypeMapper imageTypeMapper;

    //图片上传到服务器以及数据库
    @PostMapping("/upload") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    //@ResponseBody
    public ClincialResult uplaod(@RequestParam("file") MultipartFile file) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
        ClincialResult upimage = null;
        try {
            upimage = uploadServicesImpl.upimage(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ClincialResult.build(404, "上传失败");
        }
        return upimage;
    }

    //图片删除，前台传入图片Id
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("imageId") String imageId) {
        System.out.println(imageId);
        if (imageId == null) {
            return "未传入imageId,请传入imageId";
        }
        uploadServicesImpl.deleteImage(imageId);
        return "删除成功";
    }

    //图片更新
    @RequestMapping("/update")
    public @ResponseBody
    ClincialResult update(String imageId, String imgForm) {
        System.out.println("前台传过来的imageid" + imageId);
        JSONObject jsonForm = JSONObject.fromObject(imgForm);
        if (imageId.isEmpty()) {
            throw new NullException("未接收到imageId！");
        } else if (jsonForm.isEmpty()) {
            throw new NullException("数据jsonForm为空！");
        } else {
            String image_details = jsonForm.getString("image_details");
            String image_type = jsonForm.getString("image_type");
            String time = jsonForm.getString("time");
            ClincialResult updateImage = uploadServicesImpl.updateImage(image_details, image_type, time, imageId);
            return updateImage;
        }
    }
}