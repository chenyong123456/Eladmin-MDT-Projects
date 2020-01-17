package cn.knowimage.service;

public interface UserService {

    public static long sdkAppId = 1400286888;

    public static String key = "0c3219fd2a3b9b8dbf125b34d996cd018964234669a30cdf3b39b01a6bb2ad65";

    public static long expire = 10000;

    String login(String userId,String password);
}
