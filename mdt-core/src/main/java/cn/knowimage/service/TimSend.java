package cn.knowimage.service;

import com.alibaba.fastjson.JSONObject;


public interface TimSend {

    /**
     * 初始化jsonExample
     * @param Json
     * @return
     */
    Object init(String Json);

//    发送请求并返回响应数据
    String sendRequest(String url, JSONObject jsonObject);

}
