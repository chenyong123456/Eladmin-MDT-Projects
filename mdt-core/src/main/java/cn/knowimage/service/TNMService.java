package cn.knowimage.service;

import cn.knowimage.pojo.vo.TNM;

import java.util.List;

public interface TNMService {
    // 根据TNM_KEY和疾病名称找到唯一的TNM_ID
    Integer findByTNM(String key, String disease);

    //根据MDT_ID找到该MDT的所有TNM
    List<TNM> findAllByMDT(int mid);

    /**
     *  修改TNM关系表，新增TNM
     * @param key TNM_KEY
     * @param mid MDT_ID
     * @param uid 用户id
     * @param disease 疾病名称
     * @return success：TNM列表
     *         error: null mdt，user，tnm不存在
     */
    List<TNM> insertByTNM(String key, int mid, int uid, String disease);
}
