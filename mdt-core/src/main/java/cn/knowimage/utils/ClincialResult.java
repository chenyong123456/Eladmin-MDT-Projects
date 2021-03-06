package cn.knowimage.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

/**
 * 定义响应结构的结果自定义
 * @author yong.Mr
 * @date 2019-12-18
 * @version 1.0
 */
@Data
public class ClincialResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public ClincialResult() {

    }

    public ClincialResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ClincialResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static ClincialResult build(Integer status, String msg, Object data) {
        return new ClincialResult(status, msg, data);
    }

    public static ClincialResult ok(Object data) {

        return new ClincialResult(data);
    }

    public static ClincialResult ok() {

        return new ClincialResult(null);
    }

    public static ClincialResult build(Integer status, String msg) {
        return new ClincialResult(status, msg, null);
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    /**
     * 将json结果集转化为ClincialResult对象
     *
     * @param jsonData json数据
     * @param clazz    ClincialResult中的object类型
     * @return
     */
    public static ClincialResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ClincialResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ClincialResult format(String json) {
        try {
            return MAPPER.readValue(json, ClincialResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static ClincialResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }


}
