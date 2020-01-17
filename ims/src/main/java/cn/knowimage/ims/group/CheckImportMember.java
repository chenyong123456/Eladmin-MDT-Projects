package cn.knowimage.ims.group;


import cn.knowimage.ims.utils.HttpClientUtil;
import cn.knowimage.ims.vo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类为封装该用户是否导入腾讯云的接口的相关信息
 * @author yong.Mr
 * @data 2019-12-03
 * @version 1.0
 */
@Component
public class CheckImportMember {

    /**
     * 判断成员是否导入到腾讯云中
     * @param user 需要
     * @return boolean
     */
    public static boolean checkImport(List<User> user, String batchImport, String whetherImport){
        System.out.println("请求批量导入成员腾讯云的URL是否加载进来了----->:" + batchImport);
        System.out.println("请求腾讯云的URL是否加载进来了----->:" + whetherImport);
        JSONObject RequestBody = new JSONObject();
        JSONObject checkId = null;
        JSONArray CheckItem = new JSONArray();
        // 拼接需要check的成员
        for (int i = 0; i < user.size(); i++) {
            checkId = new JSONObject();
            checkId.put("UserID", user.get(i).getUsername());
            CheckItem.add(checkId);
        }
        RequestBody.put("CheckItem", CheckItem);
        // 请求腾讯云接口, 看该成员是否导入到腾讯云中
        System.out.println("检查群成员是否导入到腾讯云中的状态----->:" + RequestBody.toString());
        String result = HttpClientUtil.doPostJson(whetherImport, RequestBody.toString());

        JSONObject responseResult = JSONObject.fromObject(result);
        JSONObject noImportMember = new JSONObject();
        JSONArray importMemberList = new JSONArray();
        //查看没有导入的成员进行导入到腾讯云服务中
        if("OK".equals(responseResult.getString("ActionStatus"))){
            JSONArray resultItem = responseResult.getJSONArray("ResultItem");
            for (int i = 0; i < resultItem.size(); i++) {
               if("NotImported".equals(resultItem.getJSONObject(i).getString("AccountStatus"))){
                    //将没有导入的成员导入到腾讯云服务中
                   importMemberList.add(resultItem.getJSONObject(i).getString("UserID"));
               }
            }
        }
        noImportMember.put("Accounts", importMemberList);
        String reaction = HttpClientUtil.doPostJson(batchImport, noImportMember.toString());
        //判断是否有账号导入失败
        if(JSONObject.fromObject(reaction).getJSONArray("FailAccounts").size() == 0){
            return true;
        }
        return false;
    }

}
