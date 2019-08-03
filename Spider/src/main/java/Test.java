//import com.alibaba.fastjson.JSONObject;
//import com.tyd.spider.Parser.Parser;
//import com.tyd.spider.Utils.Decryption;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class Test {
//
//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        String url = "http://www.mafengwo.cn/mdd/base/list/pagedata_citylist";
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(url);
//        List<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>();
//
//        JSONObject obj = new JSONObject();
//        obj.put("mddid","11157");
//        obj.put("page","2");
//        JSONObject params = Decryption.getParams(obj);
//        Set<String> ks = params.keySet();
//        for(String key: ks){
//            formList.add(new BasicNameValuePair(key, params.getString(key)));
//        }
//
//        StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
//        post.setEntity(entity);
//
//        post.setHeader("Accept", "text/html;charset=utf-8");
//        post.setHeader("Accept-Encoding","gzip, deflate");
//        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
//        post.setHeader("Connection","keep-alive");
//        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        post.setHeader("Host","www.mafengwo.cn");
//        post.setHeader("Origin","http://www.mafengwo.cn");
//        post.setHeader("Referer","http://www.mafengwo.cn/mdd/citylist/11157.html");
//        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
//        post.setHeader("X-Requested-With","XMLHttpRequest");
//
//        CloseableHttpResponse response = client.execute(post);
//        int statusCode = response.getStatusLine().getStatusCode();
//        if (statusCode == 200) {
//            String resStr = EntityUtils.toString(response.getEntity());
//            //Unicode转字符
//            String result = Parser.unicodeToString(resStr);
//            System.out.println("Success: " + result);
//        } else {
//            System.out.println("Failure with Status Code: " + statusCode);
//        }
//        //关闭response和client
//        response.close();
//        client.close();
//    }
//}
