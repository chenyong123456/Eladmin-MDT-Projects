package cn.knowimage.service;

import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.pojo.instance.MDT;
import cn.knowimage.utils.ClincialResult;


/**
 * 处理病史的相关service
 * @author yong.Mr
 * @date 2020-01-07
 * @version 1.0.0
 */
public interface IllnessHistoryService {
    /**
     * 添加病人的相关病史service
     * @param createMDTVoHistory 前台大表单传过来的数据
     * @param mdt
     * @param mdtImageid
     * @return ClincialResult
     */
    public ClincialResult insertIllnessHistory(CreateMDTVo createMDTVoHistory, MDT mdt, String mdtImageid);
}
