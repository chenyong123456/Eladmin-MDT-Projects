package cn.knowimage.service.impl;

import cn.knowimage.service.MsgPersistence;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisMsgPersistence implements MsgPersistence {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void persistence(JSONObject jsonObject) {

        String GroupId = (String) jsonObject.get("GroupId");
        String From_Account = (String) jsonObject.get("From_Account");
        String MsgTime =  jsonObject.get("MsgTime").toString();

        String array = jsonObject.get("msg").toString();

        String key = GroupId + "_" + From_Account + "_" + MsgTime;
        redisTemplate.opsForValue().set(key,array,30, TimeUnit.DAYS);
    }
}
