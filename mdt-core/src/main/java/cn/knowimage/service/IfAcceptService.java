package cn.knowimage.service;


import cn.knowimage.utils.ClincialResult;

/**
 * 判断用户是否接收该MDT的会议的接口
 * @author yong.Mr
 * @date 2020-1-7
 * @version 1.0.0
 */
public interface IfAcceptService {

    /**
     * 接口的变量和方法都不能有private的修饰符, 在JDK1.8之后(不包括JDK1.8)是可以有私有的
     */
    public static final int IP = 0; //接口中成员变量默认为public static final, 由于都是static final的常量(接口中是没有构造方法的,因此不能被实例化,只能被声明),
                                    // 在内存中直接放入静态区,
    /**
     * 删除腾讯云中群组中的成员url
     */
    public static final String deleteGroupMember = "https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member?sdkappid=1400288517&identifier=user&usersig=eJwtzEELgjAcBfDvsqsh29zyn9ClbuEpjcSbtTn*lWtMEyn67sns9Hi-B*9DyryIR*1JRnhMySp0VNoO2GLgVz-H4r26N86hIhkTlHIAydJl0ZNDr2eXUnJK6aIDdsFSAQmwjfi-oJlvH2ZvzbTzXVWfoxtgOjYcXNJGub0wfB*hWp*uRVk3h*eWfH8EvjFY&random=99999998&contenttype=json";
    /**
     * java多态的一种表现
     * 在JDK1.8之后包括(JDK1.8之后的新特性) 是可以定义static, default方法, * 但是必须实现该方法 *-----> 可以通过类名直接调用
     */
    public static String getInt() { // static方法也默认是public
        final int i = 0; // 接口中的局部变量是不能使用修饰符, 和static的, JDK1.8之后可以声明为常量final
        return "我要扩展方法!";
    }

    ClincialResult recommendOther(Integer uid, Integer mid, String groupId);

    public ClincialResult recommendOtherAgree(Integer uid, Integer mid, String groupId, Integer recommendPeopleId); //方法默认是public

    ClincialResult agreeMdt(Integer uid, Integer mid, String groupId);

    ClincialResult getPeopleMsg(int mdtId, int uid);

    ClincialResult recommendPeopleRefuse(int mdtId, int uid, String groupId);


}
