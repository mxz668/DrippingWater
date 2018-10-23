package com.xz.dripping.common.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by MABIAO on 2017/11/2.
 * 数据加解密
 */
public class AesEncoder {
    private static Logger logger = LoggerFactory.getLogger(AesEncoder.class);
    private String key;
    private String encoding = "utf-8";

    public AesEncoder() {
    }

    public String encrypt(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(this.key.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes(this.encoding);
        cipher.init(1, secretKeySpec);
        byte[] byteRresult = cipher.doFinal(byteContent);
        StringBuffer sb = new StringBuffer();

        for(int encodeStr = 0; encodeStr < byteRresult.length; ++encodeStr) {
            String hex = Integer.toHexString(byteRresult[encodeStr] & 255);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        String var13 = sb.toString();
        return var13;
    }

    public String decrypt(String content) throws Exception {
        if(content.length() < 1) {
            return null;
        } else {
            byte[] byteRresult = new byte[content.length() / 2];

            for(int kgen = 0; kgen < content.length() / 2; ++kgen) {
                int random = Integer.parseInt(content.substring(kgen * 2, kgen * 2 + 1), 16);
                int secretKey = Integer.parseInt(content.substring(kgen * 2 + 1, kgen * 2 + 2), 16);
                byteRresult[kgen] = (byte)(random * 16 + secretKey);
            }

            KeyGenerator var10 = KeyGenerator.getInstance("AES");
            SecureRandom var11 = SecureRandom.getInstance("SHA1PRNG");
            var11.setSeed(this.key.getBytes());
            var10.init(128, var11);
            SecretKey var12 = var10.generateKey();
            byte[] enCodeFormat = var12.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result, this.encoding);
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public static void main(String[] args) throws Exception {
//        AesEncoder aesEncoder = new AesEncoder();
//        aesEncoder.setKey("e#DxeTyUTNu@XJpU-u");
//        String str = aesEncoder.encrypt("{\"productCode\" : \"123456\"}");
//        System.out.println(str);

        String i = Integer.toHexString(2 & 255);
        System.out.println(i);
    }
}