package cn.knowimage.mdt.createmdt.service;

import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONArray;

/**
 * 对MDT的增删改查的接口
 * @author yong.Mr
 * @Date 2019-12-31
 * @version 1.0
 */
public interface CRUDService {

    // 在接口中不能用protected,default,private修饰的, 只能使用public, 默认为public static final
    public static final String s = "";
    /**
     * 删除MDT业务逻辑
     * @return ClincialResult
     */
    ClincialResult deleteMdt(Integer mid, Integer userId);

    /**
     * 创建mdt的目的模板
     * @param mdtTemp
     * @return ClincialResult
     */
    ClincialResult createMdtGoal(String mdtTemp);

    /**
     * 获取mdtGoal的数据列表
     * @return ClincialResult
     */
    ClincialResult getMdtGoalList();

    /**
     * 删除MDTGoal的列表数据
     * @param id 删除的id
     * @return ClincialResult
     */
    ClincialResult deleteMdtGoal(Integer id);

    /**
     * 大哥PC端登录的创建的MDT的数据列表
     * @param uid 用户ID
     * @return JSONArray
     */
    JSONArray getOnselfCreateMdtList(int uid);

    /**
     * 查询MDT的主导科室和其他科室的数据列表
     * @param uid 登录人的id
     * @return ClincialResult
     */
    ClincialResult getMdtRegionOrOther(Integer uid);


}
