package com.tyd.spider.Downloader;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class Downloader {
    private String result = null;

    public String doGet(String url)throws Exception {
        HttpGet get = new HttpGet(url);
        this.setHeader(get);
        return this.getResponse(get);
    }

    public String doGet(String url, JSONObject obj)throws Exception {
        HttpGet get = new HttpGet(url);
        this.setHeader(get);
        this.setParams(get,obj);
        return this.getResponse(get);
    }

    public String doPost(String url)throws Exception {
        HttpPost post = new HttpPost(url);
        this.setHeader(post);
        return this.getResponse(post);
    }

    public String doPost(String url,JSONObject obj) throws Exception {
        HttpPost post = new HttpPost(url);
        this.setParams(post,obj);
        this.setHeader(post);
        return this.getResponse(post);
    }

    private String getResponse(HttpGet get)throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            this.result = EntityUtils.toString(response.getEntity());
            System.out.println("Success with GET: " + result);
        }else{
            System.out.println("Failure with Status Code: " + statusCode);
        }
        response.close();
        client.close();
        return result;
    }

    private String getResponse(HttpPost post)throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            this.result = EntityUtils.toString(response.getEntity());
            System.out.println("Success with POST: " + result);
        } else {
            System.out.println("Failure with Status Code: " + statusCode);
        }
        response.close();
        client.close();
        return  result;
    }

    abstract void setHeader(HttpGet get);

    abstract void setHeader(HttpPost post);

    abstract void setParams(HttpPost post,JSONObject obj)throws Exception;

    abstract void setParams(HttpGet get,JSONObject obj);

}
