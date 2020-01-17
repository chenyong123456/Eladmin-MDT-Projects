package cn.knowimage.service.impl;

import cn.knowimage.pojo.vo.GroupWechatTreatment;
import cn.knowimage.mapper.AcceptTreatmentMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkNameServiceImpl {
    @Autowired
    private AcceptTreatmentMapper acceptTreatmentMapper;
    public JSONObject RemarkInfo(Integer mdtId) {
        JSONObject json = new JSONObject();
        int i = 0;
        List<GroupWechatTreatment> doctorName= acceptTreatmentMapper.selectDoctorNameByMdt(mdtId);
        //查询所有的医生姓名
        //创建一个大对象
        for (GroupWechatTreatment name: doctorName) {
            JSONObject commentJson = new JSONObject();
            commentJson.put("Doctorment", name.getUsername());
            String s=acceptTreatmentMapper.selectRemarkByMdtCreator(mdtId, name.getCreator());
            if (s.equals("null")){
                commentJson.put("Remark", false);
            }else {
                commentJson.put("Remark",s);
            }
            System.out.println("备注---》"+s);
            //创建不重复id
            //将每次生成的存放的医生姓名和备注的json对象放入大对象
            i++;
            json.put(i,commentJson);
        }
        return  json;
    }
}
