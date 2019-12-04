package com.util;

import java.util.Arrays;

/**
 * 类名称: CheckUtil
 * 类描述: 请求校验
 * @Author: tanjianjun
 * @Date: 2019/11/14 19:20
 * @Version 1.0
 */

public class CheckUtil {

    private static final String token = "nuoche";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] str = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for(int i =0 ;i<str.length;i++){
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }
}
