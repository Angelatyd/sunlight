package com.tyd.spider.Utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Decryption {

    public static JSONObject getParams(JSONObject content) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String _ts = Long.toString(System.currentTimeMillis());
        content.put("_ts",_ts);
        content = sortByKey(content);
        byte[] buffer = (content.toString()+getConstant()).getBytes("utf-8");
        //MD5加密
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buffer);
        byte[] resultArray = md5.digest();
        BigInteger bigInt = new BigInteger(1, resultArray);
        String _sn = bigInt.toString(16).substring(2,12);
        content.put("_sn",_sn);
        return content;
    }

    private static JSONObject sortByKey(JSONObject data){
        //确保toString()保持顺序
        JSONObject newObj = new JSONObject(true);
        List<String> list = new ArrayList<String>(data.keySet());
        Collections.sort(list);
        for(String key: list){
            newObj.put(key,data.get(key));
        }
        return newObj;
    }

    private static String getConstant(){
        //读取js文件取变量__Ox2133f
        //获取数组第47个字符串
        //取出16进制数字组成字符串数组
        //字符串数组转字节数组，decode utf-8
        return "c9d6618dbc657b41a66eb0af952906f1";
    }

}
