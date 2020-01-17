package cn.knowimage.service;

import cn.knowimage.pojo.instance.Decision;

import java.util.List;


public interface DecisionService {

    //新增决策
    Decision insert(Decision decision);

    /**
     * 根据MDTid获得该MDT的所有决策
     * @param mid
     * @return
     */
    List<Decision> getAllByMDT(int mid);

}
