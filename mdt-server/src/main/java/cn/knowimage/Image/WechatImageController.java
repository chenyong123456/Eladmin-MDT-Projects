package cn.knowimage.Image;

import cn.knowimage.exception.NullException;
import cn.knowimage.service.impl.WechatImageInfoImpl;
import cn.knowimage.utils.ClincialResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/wechatImage")
public class WechatImageController {
    @Autowired
    private WechatImageInfoImpl wechatImageInfo;
    @RequestMapping("/select")
    public @ResponseBody
    ClincialResult wechatImageInfo(Integer mdtId) {
        System.out.println("mdtId---》" + mdtId);
        if (mdtId == null) {
            throw new NullException("未传入mdtId！！！！！");
        } else {
            ClincialResult wechatImage = wechatImageInfo.wechatImageinformation(mdtId);
            return wechatImage;
        }
    }
}
