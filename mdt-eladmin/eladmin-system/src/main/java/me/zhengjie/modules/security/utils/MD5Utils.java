//package me.zhengjie.modules.security.utils;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import org.apache.commons.lang3.StringUtils;
//
///**
// * @Package：cn.ucaner.alpaca.framework.utils.encrypt
// * @ClassName：MD5Utils
// * @Description：   <p> MD5加密工具    By Jason </p>
// * @Author： - Jason
// * @CreatTime：2018年5月24日 下午9:40:31
// * @Modify By：
// * @ModifyTime：  2018年5月24日
// * @Modify marker：
// * @version    V1.0
// */
//public class MD5Utils {
//  protected final static String MD5_KEY = "MD5";
//
//  protected final static String SHA_KEY = "SHA1";
//
//  /**
//   * @param value
//   * @param key
//   * @return
//   */
//  protected static String encrypt(String value, String key) {
//    try {
//      // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
//      MessageDigest messageDigest = MessageDigest.getInstance(key);
//      // 输入的字符串转换成字节数组
//      byte[] inputByteArray = value.getBytes();
//      // inputByteArray是输入字符串转换得到的字节数组
//      messageDigest.update(inputByteArray);
//      // 转换并返回结果，也是字节数组，包含16个元素
//      byte[] resultByteArray = messageDigest.digest();
//      // 字符数组转换成字符串返回
//      return byteArrayToHex(resultByteArray);
//    } catch (NoSuchAlgorithmException e) {
//      return null;
//    }
//  }
//
//  /**
//   * 字节数组转换为hex
//   *
//   * @param byteArray
//   * @return
//   */
//  private static String byteArrayToHex(byte[] byteArray) {
//
//    // 首先初始化一个字符数组，用来存放每个16进制字符
//    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//    // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
//    char[] resultCharArray = new char[byteArray.length * 2];
//    // 遍历字节数组，通过位运算（位运算效率高）,转换成字符放到字符数组中去
//    int index = 0;
//    for (byte b : byteArray) {
//      resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
//      resultCharArray[index++] = hexDigits[b & 0xf];
//    }
//    // 字符数组组合成字符串返回
//    return new String(resultCharArray);
//  }
//
//  /**
//   * 获得16位的加密字符
//   *
//   * @param str
//   * @return
//   * @throws NoSuchAlgorithmException
//   */
//  public static String getMd5String16(String str) throws NoSuchAlgorithmException {
//    String md5str = getMd5String32(str).substring(8);
//    return md5str.substring(0, md5str.length() - 8);
//  }
//
//  /**
//   * 获得24位的MD5加密字符
//   *
//   * @param str
//   * @return
//   * @throws NoSuchAlgorithmException
//   */
//  public static String getMd5String24(String str) throws NoSuchAlgorithmException {
//
//    String md5str = getMd5String32(str).substring(4);
//    return md5str.substring(0, md5str.length() - 4);
//  }
//
//  /**
//   * 获得32位的MD5加密算法
//   *
//   * @param str
//   * @return
//   * @throws NoSuchAlgorithmException
//   */
//  public static String getMd5String32(String str) throws NoSuchAlgorithmException {
//    MessageDigest md = MessageDigest.getInstance("MD5");
//    md.update(str.getBytes());
//    byte b[] = md.digest();
//    int i;
//    StringBuffer buf = new StringBuffer();
//    for (int offset = 0; offset < b.length; offset++) {
//      i = b[offset];
//
//      if (i < 0)
//        i += 256;
//
//      if (i < 16)
//        buf.append("0");
//
//      buf.append(Integer.toHexString(i));
//    }
//    return buf.toString();
//  }
//
//  /**
//   * 获取MD5密码
//   *
//   * @param password
//   * @param salt
//   * @return
//   * @throws NoSuchAlgorithmException
//   */
//  public static String getMD5Pwd(String password, String salt) throws NoSuchAlgorithmException {
//    String result = null;
//    if (StringUtils.isNotBlank(salt)) {
//      result = getMD5(getMD5(password) + salt);
//    } else {
//      result = getMD5(password);
//    }
//    return result;
//  }
//
//  /**
//   * 获取MD5加密数据
//   *
//   * @param input
//   * @return
//   * @throws NoSuchAlgorithmException
//   */
//  public static String getMD5(String input) throws NoSuchAlgorithmException {
//    String result = input;
//    if (input != null) {
//      //or "SHA-1"
//      MessageDigest md = MessageDigest.getInstance("MD5");
//      md.update(input.getBytes());
//      BigInteger hash = new BigInteger(1, md.digest());
//      result = hash.toString(16);
//      //40 for SHA-1
//      while (result.length() < 32) {
//        result = "0" + result;
//      }
//    }
//    return result;
//  }
//
//  /**
//   * For test by Jason
//   */
//  public static void main(String[] args) {
//    try {
//      //829018f9dbd65fb8
//      System.out.println(getMd5String16("Jason"));
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }
//  }
//}
