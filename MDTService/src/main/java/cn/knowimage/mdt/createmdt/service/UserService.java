package cn.knowimage.mdt.createmdt.service;

public interface UserService {

    /**
     * 接口中成员变量默认为public static final, 由于都是static final的常量(接口中是没有构造方法的,因此不能被实例化,只能被声明),
     * 在内存中直接放入静态区,
     * jvm主要分为4个存储区: 常量池, 堆, 栈, 方法区(也叫静态区)
     */
    public static final long sdkAppId = 1400286888;

    public static final String key = "0c3219fd2a3b9b8dbf125b34d996cd018964234669a30cdf3b39b01a6bb2ad65";

    public static final long expire = 10000;

    String login(String userId, String password);

    /**
     * 可以直接用类名进行调用
     * @return String
     */
    /*public static String getKey(){
        final int i = 0;
        return "";
    }*/
}
