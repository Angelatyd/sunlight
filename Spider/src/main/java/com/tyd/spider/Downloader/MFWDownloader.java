package com.tyd.spider.Downloader;

import com.alibaba.fastjson.JSONObject;
import com.tyd.spider.Utils.Decryption;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MFWDownloader extends Downloader {

    public void setHeader(HttpGet get){
        get.setHeader("Accept", "text/html;charset=utf-8");
        get.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        get.setHeader("Host","www.mafengwo.cn");
        get.setHeader("Referer","http://www.mafengwo.cn/");
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
    }

    public void setHeader(HttpPost post){
        post.setHeader("Accept", "text/html;charset=utf-8");
        post.setHeader("Accept-Encoding","gzip, deflate");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Connection","keep-alive");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        post.setHeader("Host","www.mafengwo.cn");
        post.setHeader("Origin","http://www.mafengwo.cn");
        post.setHeader("Referer","http://www.mafengwo.cn/mdd/citylist/11157.html");
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        post.setHeader("X-Requested-With","XMLHttpRequest");
    }

    public void setParams(HttpPost post,JSONObject obj)throws NoSuchAlgorithmException, UnsupportedEncodingException {
        List<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>();
        JSONObject params = Decryption.getParams(obj);
        Set<String> ks = params.keySet();
        for(String key: ks){
            formList.add(new BasicNameValuePair(key, params.getString(key)));
        }
        StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
        post.setEntity(entity);
    }

    public void setParams(HttpGet get,JSONObject obj){
    }
}
