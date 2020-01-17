package cn.knowimage.service.impl;

import cn.knowimage.service.CallBack;
import cn.knowimage.service.MsgPersistence;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CallbackAfterSendMsg implements CallBack {

    private static final String TIMTextElem = "TIMTextElem"; // 文本类型数据

    @Autowired
    @Qualifier(value = "redisMsgPersistence")
    private MsgPersistence msgPersistence;

    @Override
    public String deal(String jsonStr) {

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        JSONArray jsonArray = jsonObject.getJSONArray("MsgBody");

        List<String> list = new ArrayList<>();

        for(int i = 0;i < jsonArray.size();++i){
            JSONObject object = jsonArray.getJSONObject(i);
            String MsgType = (String) object.get("MsgType");

            if(MsgType.equals(TIMTextElem)){
                JSONObject jsonObject1 = object.getJSONObject("MsgContent");
                list.add(jsonObject1.get("Text").toString());
            }
        }

        JSONObject object = new JSONObject();
        object.put("GroupId", jsonObject.get("GroupId"));
        object.put("From_Account", jsonObject.get("From_Account"));
        object.put("MsgTime", jsonObject.get("MsgTime"));

        JSONArray array = JSON.parseArray(JSON.toJSONString(list));

        object.put("msg", array);

        msgPersistence.persistence(object);

        return null;
    }

}
