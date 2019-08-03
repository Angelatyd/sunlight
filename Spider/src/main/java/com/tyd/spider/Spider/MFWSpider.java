package com.tyd.spider.Spider;

import com.alibaba.fastjson.JSONObject;
import com.tyd.spider.Bean.CityBean;
import com.tyd.spider.Bean.CountryBean;
import com.tyd.spider.Downloader.MFWDownloader;
import com.tyd.spider.Parser.Parser;
import com.tyd.spider.Storage.Storage;
import com.tyd.spider.UrlManager.UrlManager;

import java.util.List;

public class MFWSpider {
    private UrlManager urlManager;
    private MFWDownloader downloader;
    private Parser parser;
    private Storage storage;

    public MFWSpider(){
        this.urlManager = new UrlManager();
        this.downloader = new MFWDownloader();
        this.parser = new Parser();
        this.storage = new Storage();
    }

    public void cityCrawler() throws Exception {
        String url = "http://www.mafengwo.cn/mdd/base/list/pagedata_citylist";
        JSONObject obj = new JSONObject();
        obj.put("mddid","11157");
        obj.put("page","1");
        String result = downloader.doPost(url,obj);
        String chResult = parser.unicodeToString(result);
        List<CityBean> cityList = parser.parseCity(chResult);
    }

    public void countryCrawler(String url)throws Exception {
        String result = downloader.doGet(url);
        List<CountryBean> countryList = parser.parseCountry(result);
        for(CountryBean country: countryList){
            System.out.println(country.getLink());
            System.out.println(country.getId());
            System.out.println(country.getChineseName());
            System.out.println(country.getEnglishName());
            System.out.println(country.getContinent());
            System.out.println("----------------------");
        }
    }

    public static void main(String[] args) throws Exception{
        String url = UrlManager.getUrl();
        MFWSpider spider = new MFWSpider();
        spider.countryCrawler(url);
    }
}
