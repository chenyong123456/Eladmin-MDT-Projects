package me.zhengjie.modules.security.utils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * AES解密 用于微信小程序获取unionId
 */
public class AES {

  /**
   *  * AES解密
   *  * @param content 密文
   *  * @return
   *  * @throws InvalidAlgorithmParameterException
   *  * @throws NoSuchProviderException
   *  
   */
  public static final String ALGORITHM = "AES/CBC/PKCS7Padding";

  public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
    try {
      Security.addProvider(new BouncyCastleProvider());
      Cipher cipher = Cipher.getInstance(ALGORITHM,"BC");
      Key sKeySpec = new SecretKeySpec(keyByte, "AES");
    //  cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
      cipher.init(Cipher.DECRYPT_MODE,sKeySpec,generateIV(ivByte));
      byte[] result = cipher.doFinal(content);
      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public static AlgorithmParameters generateIV(byte[] iv) throws Exception{ AlgorithmParameters params = AlgorithmParameters.getInstance("AES"); params.init(new IvParameterSpec(iv)); return params; }
}
