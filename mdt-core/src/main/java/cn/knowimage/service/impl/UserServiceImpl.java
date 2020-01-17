package cn.knowimage.service.impl;

import cn.knowimage.ims.vo.User;
import cn.knowimage.mapper.UserMapper;
import cn.knowimage.service.UserService;
import com.tencentyun.TLSSigAPIv2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String userId, String password) {

        System.out.println(userId);
        User user = userMapper.findById(userId);
        if (user == null) return null;
        //腾讯云生成User
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(sdkAppId, key);
        String userSig = tlsSigAPIv2.genSig(userId, expire);

        return userSig;
    }
}
