package cn.knowimage.mapper;

import cn.knowimage.ims.vo.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Select("select user_login.* from user_login where username = #{userId}")
    User findById(String userId);

}
