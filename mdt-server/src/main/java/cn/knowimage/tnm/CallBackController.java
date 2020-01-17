package cn.knowimage.tnm;

import cn.knowimage.service.CallBack;
import cn.knowimage.service.impl.TimFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CallBackController {

    @Autowired
    private TimFactory timFactory;

    @RequestMapping(value = "callback",produces = "application/json")
    public String callback(@RequestBody String jsonStr, @RequestParam String SdkAppid) throws ClassNotFoundException {
        CallBack callBack = timFactory.buildCallBack(jsonStr);
        callBack.deal(jsonStr);
        return null;
    }
}
