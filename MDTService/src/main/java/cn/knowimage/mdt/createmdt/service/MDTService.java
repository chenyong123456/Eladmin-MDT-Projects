package cn.knowimage.mdt.createmdt.service;


import cn.knowimage.ims.vo.CreateMDTVo;
import cn.knowimage.utils.ClincialResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface MDTService {

    JSONObject getMDTListByUser(int uid, int dataNumber, int page, String queryName, String queryConsultation);

    ClincialResult insertMDT(CreateMDTVo createMDTVo, int uid);

    // 以下为微信的接口
    JSONArray getMDTList(int uid, String url);


    // 大哥的eladmin后台的接口参与者的接口
    JSONArray getMdtParticition(int uid);


}
