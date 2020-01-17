package cn.knowimage.service.impl;

import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.service.TimSend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class TimSendImpl implements TimSend {
    /**
     * 此实现类的初始化方法 会将字符串初始化为空字符串，整型初始化为0，如出现其他类型将会警告
     * @param Json
     * @return
     */
    @Override
    public Object init(String Json) {

        if(JSON.isValidArray(Json)){
            JSONArray jsonArray = JSONArray.parseArray(Json);

            for(int i = 0;i < jsonArray.size();++i){
                Object o = jsonArray.get(i);
                if(o instanceof String){
                    jsonArray.set(i,"");
                }else if(o instanceof JSONObject){
                    jsonArray.set(i,init(o.toString()));
                }else if(o instanceof JSONArray){
                    jsonArray.set(i,init(o.toString()));
                }else if(o instanceof Integer){
                    jsonArray.set(i,0);
                }else{
                    log.warn("类型不匹配");
                }
            }
            return jsonArray;
        }

        if(JSON.isValidObject(Json)){
            JSONObject jsonObject = JSONObject.parseObject(Json);
            for(Map.Entry entry : jsonObject.entrySet()){
                Object o = entry.getValue();
                if(o instanceof String){
                    entry.setValue("");
                }else if(o instanceof JSONObject){
                    entry.setValue(init(o.toString()));
                }else if(o instanceof JSONArray){
                    entry.setValue(init(o.toString()));
                }else if(o instanceof Integer){
                    entry.setValue(0);
                }else{
                    log.warn("类型不匹配");
                }
            }
            return jsonObject;
        }
        throw new RuntimeException("json格式错误");
    }

    @Override
    public String sendRequest(String url, JSONObject jsonObject) {
        String res = HttpClientUtil.doPostJson(url,jsonObject.toJSONString());
        return res;
    }
}
