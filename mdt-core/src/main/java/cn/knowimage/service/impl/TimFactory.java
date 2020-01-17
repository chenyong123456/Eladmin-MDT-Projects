package cn.knowimage.service.impl;

import cn.knowimage.service.CallBack;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 *  腾讯云接口工厂，主要用来生成与腾讯云相关的各种接口
 *  后台开发时，请务必将类开发到指定位置，并使用请求体的command来做类的名称
 */

@Service
public class TimFactory {

    @Autowired
    private ApplicationContext applicationContext;

    //后台回调开发类的存放位置
    private static final String classPath = "hyly.mdt.service.tim.impl";


    /**
     * 构造并返回 回调接口
     * @param jsonStr 请求体的json数据
     * @return CallBack 接口
     * @throws ClassNotFoundException
     */
    public CallBack buildCallBack(String jsonStr) throws ClassNotFoundException{
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String command = (String) jsonObject.get("CallbackCommand");
        String className = command.split("\\.")[1];
        Class clazz = Class.forName(classPath + "." + className);
        return (CallBack) applicationContext.getBean(clazz);
    }


}
