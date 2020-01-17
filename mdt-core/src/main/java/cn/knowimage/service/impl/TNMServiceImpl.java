package cn.knowimage.service.impl;

import cn.knowimage.mapper.MDTMapper;
import cn.knowimage.mapper.UserMapper;
import cn.knowimage.pojo.vo.TNM;
import cn.knowimage.mapper.TNMMapper;
import cn.knowimage.service.TNMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TNMServiceImpl implements TNMService {

    @Autowired
    private TNMMapper tnmMapper;

    @Autowired
    private MDTMapper mdtMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer findByTNM(String key,String disease) {
        return tnmMapper.findByTNM(key,disease);
    }

    @Override
    public List<TNM> findAllByMDT(int mid) {
        return tnmMapper.findTNMById(mid);
    }

    @Override
    public List<TNM> insertByTNM(String key, int mid, int uid,String disease) {
        if(userMapper.selectByPrimaryKey(uid) == null) return null;
        if(mdtMapper.selectByPrimaryKey(mid) == null) return null;
        Integer tnm_id = findByTNM(key,disease);
        if(tnm_id == null) return null;
        tnmMapper.insertTNM(mid,uid,tnm_id);
        System.out.println("返回给前端的tnm分级数据"+tnmMapper.findTNMById(mid));
        return tnmMapper.findTNMById(mid);
    }
}
