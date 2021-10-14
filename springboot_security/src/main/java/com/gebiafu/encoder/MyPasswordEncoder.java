package com.gebiafu.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * title: MyPasswordEncoder
 * author: Gebiafu
 * date: 2021/06/30 13:30
 * 必须spring容器管理当前类型的对象
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        //获得加密算法对象,javaSE提供的api
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //加密,参数和返回值都是字节数组,返回值数组长度一定是16
            byte[] tmp = messageDigest.digest(charSequence.toString().getBytes());
            System.out.println("加密后的字节数组长度是:"+tmp.length);
            //把每个字节转成一个00-FF的16进制数字字符串.16个字节,总长度32,字符串内容是0-f
            StringBuilder builder=new StringBuilder("");
            for(byte b:tmp){
                String s = Integer.toHexString(b & 0xFF);
                if(s.length()==1){
                    //.代表字节b取值小于16,补充一个0
                    builder.append("0");
                }
                builder.append(s);//拼接16进制数字
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @param charSequence 请求参数密码
     * @param s 正确密码(理论上是加密后的密码)
     * @return
     */

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }
}
