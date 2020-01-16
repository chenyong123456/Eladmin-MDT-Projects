package cn.knowimage.mdt.controller;



import cn.knowimage.utils.ClincialResult;
import cn.knowimage.mdt.createmdt.service.CRUDService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 对MDT的增删改查的业务操作
 * @author yong.Mr
 * @Date 2019-12-31
 * @version 1.0
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/cruds")
public class CRUDController {

    /**
     * 注入CRUDService接口
     */
    @Autowired
    private CRUDService crudService;

    /**
     * 删除MDT业务逻辑
     * @param mid MDT的id
     * @param userId 用户的ID
     * @return ClincialResult 响应式结果
     */
    @RequestMapping("/del")
    public ClincialResult deleteMdt(@RequestParam(required = true) Integer mid, @RequestParam(required = true) Integer userId) {
        System.out.println("******************************删除MDT的业务逻辑******************************");
        System.out.println("大哥传过来的MDTid的数据格式----->:" + mid);
        System.out.println("大哥传过来的登录用户的userId的数据格式----->:" + userId);
        // 调用service的接口
        ClincialResult clincialResult = crudService.deleteMdt(mid, userId);
        System.out.println("******************************删除MDT的业务逻辑******************************");
        // 返回service的接口
        return clincialResult;
    }

    /**
     * 创建mdt的目的模板 - 提交方式为post提交
     * @param mdtTemp 前台传过来数据
     * @return ClincialResult
     */
    @RequestMapping(value = "/mdtGoal",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public ClincialResult createMdtGoal(String mdtTemp){
        System.out.println("*************************创建mdt的目的模板*************************");
        ClincialResult mdtGoal = crudService.createMdtGoal(mdtTemp);
        System.out.println("*************************创建mdt的目的模板*************************");
        return  mdtGoal;
    }

    /**
     * 获取mdt的列表 - 提交方式为GET提交
     * @return ClincialResult
     */
    @RequestMapping(value = "/getMdtGoalList",method = RequestMethod.GET)
    public ClincialResult getMdtGoalList(){
        System.out.println("**************************查询MDT的模板数据列表**************************");
        ClincialResult mdtGoalList = crudService.getMdtGoalList();
        System.out.println("**************************查询MDT的模板数据列表**************************");
        return mdtGoalList;

    }

    /**
     * 删除GoalTemplate的书
     * @param id 该模板的数据id
     * @return ClincialResult
     */
    @RequestMapping(value = "deleteGoal",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,method = RequestMethod.POST)
    public ClincialResult deleteMdtGoal(Integer id){
        System.out.println("**************************删除MDT的数据列表**************************");
        System.out.println("前台数据是否传入入----->:" + id);
        ClincialResult clincialResult = crudService.deleteMdtGoal(id);
        System.out.println("**************************删除MDT的数据列表**************************");
        return clincialResult;
    }

    /**
     * 大哥PC端的本人创建MDT的列表
     * @param uid 登陆人的ID
     * @return JSONArray
     */
    @RequestMapping("/getOnselfCreateMdtList")
    public JSONArray getOnselfCreateMdtList(int uid){
        System.out.println("***************************************PC端是登录人创建的MDT列表***************************************");
        System.out.println("大哥是数据是否传入进来----->:" + uid);
        JSONArray onselfCreateMdtList = crudService.getOnselfCreateMdtList(uid);
        System.out.println("返回前台的数据为----->:" + onselfCreateMdtList.toString());
        System.out.println("***************************************PC端是登录人创建的MDT列表***************************************");
        return onselfCreateMdtList;

    }
}
