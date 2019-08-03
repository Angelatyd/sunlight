package com.tyd.spider.Parser;

import com.tyd.spider.Bean.CityBean;
import com.tyd.spider.Bean.CountryBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public String unicodeToString(String str){
        Pattern pattern = Pattern.compile("(?<=\\\\u)\\w{4}");
        Matcher matcher = pattern.matcher(str);
        //迭代器机制
        while(matcher.find()) {
            char ch = (char) Integer.parseInt(matcher.group(),16);
            String target = Character.toString(ch);
            str = str.replace(matcher.group(),target);
        }
        return str.replace("\\u","");
    }

    public List<CountryBean> parseCountry(String result){
        List<CountryBean> countryList = new ArrayList<CountryBean>();
        Document doc = Jsoup.parse(result);
        Elements elements = doc.select("dl[class='item']");
        for(Element item: elements){
            String continent = item.getElementsByTag("dt").get(0).text();
            Elements uls = item.getElementsByTag("dd").get(0).getElementsByTag("ul");
            for(Element ul: uls){
                Elements lis = ul.getElementsByTag("li");
                for(Element li: lis){
                    if(!li.hasAttr("class")){
                        CountryBean country = new CountryBean();
                        Element target = li.getElementsByTag("a").get(0);
                        String link = target.attr("href");
                        String names = target.text();
                        int index = names.indexOf(" ");
                        country.setLink(link);
                        country.setId(link.replace("/travel-scenic-spot/mafengwo/","").replace(".html",""));
                        country.setChineseName(names.substring(0,index));
                        country.setEnglishName(names.substring(index+1));
                        country.setContinent(continent);
                        countryList.add(country);
                    }
                }
            }
        }
        return countryList;
    }

    public List<CityBean> parseCity(String result){
        List<CityBean> cityList = new ArrayList<CityBean>();
        Document doc = Jsoup.parse(result);
        Elements elements = doc.getElementsByTag("li");
        for(Element item: elements){
            CityBean city = new CityBean();
            Element nameElement = item.getElementsByClass("img").get(0).getElementsByTag("a").get(0).getElementsByClass("title").get(0);
            Element numElement = item.getElementsByClass("caption").get(0).getElementsByTag("dt").get(0).getElementsByClass("nums").get(0);
            String names = nameElement.text();
            if(!"".equals(names) && names != null){
                if(" ".equals(names.substring(0,1))){
                    names = names.substring(1);
                }
                int index = names.indexOf(" ");
                city.setChineseName(names.substring(0,index));
                city.setEnglishName(names.substring(index+1));
            }else{
                city.setChineseName("");
                city.setEnglishName("");
            }
            city.setNumVisitor(numElement.text());
            cityList.add(city);
        }
        return cityList;
    }

    public static void main(String[] args) {
        String html = "<li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/146836.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"146836\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://p2-q.mafengwo.net/s7/M00/FB/F7/wKgB6lRllJOALIhxAAIRIEqA4nA79.jpeg?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    朗厄兰岛                    <p class=\"enname\">Langeland</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1475</b>人去过                </div>                <div class=\"detail\">                    朗厄兰岛是丹麦菲英州岛屿，面积284平方公里，位于波罗的海。岛上建筑多为修道院和城堡，最出名的是当地的茨拉讷凯城堡，这个城堡曾是王室住地，后经过不断的完善和大力的保护，现今这里已成为旅游景点。全岛地势起伏平缓，森林茂密，土壤肥沃，适于种植谷类和甜菜。岛南部还有有保存完好的石器时代古墓。                </div>            </dt>            <dd>                                    <span class=\"label\">TOP3</span>                                                                    <a href=\"/poi/50521500.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50521500\" title=\"Spodsbjerg Turistbaadehavn\">Spodsbjerg Turistbaadehavn</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/50482816.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50482816\" title=\"Galleri Kobolt\">Galleri Kobolt</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/50532244.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50532244\" title=\"Guldtranen\">Guldtranen</a>                                                </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/61919.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"61919\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://p4-q.mafengwo.net/s6/M00/D1/0A/wKgB4lOWeE-AB2PmAAGgcpT73Wg61.jpeg?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    罗斯基勒                    <p class=\"enname\">Roskilde</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1013</b>人去过                </div>                <div class=\"detail\">                    罗斯基勒， Roskilde，丹麦西兰岛东部港口。在罗斯基勒湾顶。是哥本哈根的市郊住宅区。西兰岛最大的铁路枢纽。有酒精、纸张、农业机械制造、肉罐头、鞣革等工业。从十世纪到1443年曾是丹麦的首都。建有40个丹麦国王和王族陵墓。附近有原子能研究中心。有渔业。                </div>            </dt>            <dd>                                    <span class=\"label\">TOP3</span>                                                                    <a href=\"/poi/7640102.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7640102\" title=\"维京海盗船博物馆\">维京海盗船博物馆</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/6558557.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"6558557\" title=\"罗斯基勒大教堂\">罗斯基勒大教堂</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/7640107.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7640107\" title=\"圣劳伦缇教堂\">圣劳伦缇教堂</a>                                                </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/101205.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"101205\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://n3-q.mafengwo.net/s7/M00/2C/EB/wKgB6lT0Ea-AXuN5AAg0IZS48gg614.png?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    西西缪特                    <p class=\"enname\">Sisimiut</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1372</b>人去过                </div>                <div class=\"detail\">                    Sisimiut是格陵兰岛中西部的一个小镇，位于戴维斯海峡约320公里（200英里）以北的海岸。是Qeqqata市的行政中心和格陵兰的第二大镇，在2013年拥有5598人口。西西缪特是国内最大的商务中心，北部努克的国家首都，并且是格陵兰增长最快的城市之一。虽然镇有一个不断增长的工业基地，但是钓鱼是西西缪特的主要行业。                </div>            </dt>            <dd>                            </dd>        </dl>            </li>    <li class=\"item \" style=\"clear:both;\">        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/91513.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"91513\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://p1-q.mafengwo.net/s7/M00/AA/10/wKgB6lPXZIGAfS-HAADicnLjehE98.jpeg?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    维堡                    <p class=\"enname\">Viborg</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1478</b>人去过                </div>                <div class=\"detail\">                    维堡位于丹麦中日德兰大区，是丹麦历史悠久的城市之一，是中日德兰大区的行政中心所在地，日德兰半岛高等法院、西部高等法院都坐落在此。维堡最为著名的景点是于1130年开始建造的维堡大教堂，该教堂是丹麦最大的花岗岩教堂之一。它见证了维堡这座城市的悠久历史，向人们展示当地的人文景观。紧邻维堡大教堂的是1937年成立的维堡博物馆，全景展现斯科夫戈德家族四代艺术家们的精彩作品。此外，有着复杂多面的形状的维堡市政厅也是游客们参观的好去处，建筑顶层的咖啡厅直接通向屋顶花园，可以在花园里观赏大教堂。                </div>            </dt>            <dd>                                    <span class=\"label\">TOP3</span>                                                                    <a href=\"/poi/50485164.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50485164\" title=\"Haervejstaeppet\">Haervejstaeppet</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/50486872.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50486872\" title=\"Bruunshaab Gamle Papfabrik\">Bruunshaab Gamle Papfabrik</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/50484992.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50484992\" title=\"Butik Haugland\">Butik Haugland</a>                                                </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/61911.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"61911\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://p3-q.mafengwo.net/s6/M00/04/8C/wKgB4lOWp5CADyjVAAEw9pKYIhA59.jpeg?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    埃斯比约                    <p class=\"enname\">Esbjerg</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1148</b>人去过                </div>                <div class=\"detail\">                    埃斯比约（Esbjerg）是一个丹麦城市，位于丹麦西南部、日德兰半岛西海岸，南丹麦大区的一部份。也是丹麦第五大城市，全国最大的渔港。埃斯比约港还是北欧地区最大的渔港。现代捕鱼业是埃斯比约市的工业重心。埃斯比约市商业繁荣，有四个大的商业中心和国际一流的家具中心。市内文化艺术活动频繁。时常有地方艺术团体或著名艺术家来此演出。夏季，该市都要主办丹麦一些最盛大的摇滚音乐节。该市拥有众多博物馆，如数家专业博物馆、航海博物馆、渔业博物馆、印刷技术博物馆、城市档案馆、水族馆。                </div>            </dt>            <dd>                                    <span class=\"label\">TOP3</span>                                                                    <a href=\"/poi/7708106.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7708106\" title=\"Esbjerg Water Tower\">Esbjerg Water Tower</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/50489316.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"50489316\" title=\"Men at Sea\">Men at Sea</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/7708109.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7708109\" title=\"Esbjerg Kunstmuseum\">Esbjerg Kunstmuseum</a>                                                </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/91955.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"91955\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://b3-q.mafengwo.net/s7/M00/BE/C9/wKgB6lS85J2AIlyuAAW7Zyfv3zM545.png?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    加斯庭                    <p class=\"enname\">Gr?sten</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1490</b>人去过                </div>                <div class=\"detail\">                    加斯庭是丹麦森讷堡的一个城市，位于南丹麦。这个城市有许多在IT，电子公司，砖瓦厂和粮食生产基地。此外，还有各种各样的商店。这里交通便利，方便前往新南日德兰半岛，开车到桑德堡大约只需要15分钟。这个镇位于Tinglev和桑德堡之间，有铁路连接到哥本哈根。最有名的城堡是丹麦王室居住夏季行宫。                </div>            </dt>            <dd>                            </dd>        </dl>            </li>    <li class=\"item \" style=\"clear:both;\">        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/91670.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"91670\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://p3-q.mafengwo.net/s7/M00/B2/E9/wKgB6lS80I6AIXMRAASCdKFPAkw006.png?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    鲁德克丙                    <p class=\"enname\">Rudk?bing</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1437</b>人去过                </div>                <div class=\"detail\">                    鲁德克丙位于丹麦南部的斯文堡，是朗厄兰最大的镇，有4537个居民。它是一个古老的小镇，没有进行大规模的工业发展，因此，城市中蜿蜒的老商户房子，从远古时代就铺就的鹅卵石道路，古老的街道和小巷都别有一番风味。Rudkoebing教堂始建于12世纪，是这个城市的标志性建筑。它高高耸立的四个弧形围墙和八角形的铜镀金尖塔屋顶都有一种文艺复兴的气息。                </div>            </dt>            <dd>                            </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/91624.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"91624\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://b2-q.mafengwo.net/s7/M00/42/B8/wKgB6lPrEb2AK5ieAAYO_Q_RgXk054.png?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    桑德堡                    <p class=\"enname\">Sonderborg</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1426</b>人去过                </div>                <div class=\"detail\">                    桑德堡有S?nderborg城堡、丹麦皇家陆军士官学校和sandbjerg地产，s?nderborg城堡坐落在小镇的中心，并且建造了博物馆来集中体现该地区的历史和文化，该博物馆全年开放。sandbjerg地产曾属于S?nderborg公爵，之后为雷文特洛家庭所属，1954年捐赠给奥胡斯大学。另外，S?nderborg酒店拥有由德国军队在1906年建起了城堡般的军营，通过集中安置ALS峡湾对面的Alsion。今天的军营是家丹麦军队士官学校。                </div>            </dt>            <dd>                                    <span class=\"label\">TOP3</span>                                                                    <a href=\"/poi/7919218.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7919218\" title=\"圣玛丽教堂\">圣玛丽教堂</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/7919157.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7919157\" title=\"sonderborg castle\">sonderborg castle</a>                                                                        <span class=\"divide\"></span>                                                <a href=\"/poi/7914750.html\" target=\"_blank\"  data-type=\"POI\"  data-id=\"7914750\" title=\"森纳堡帆船港\">森纳堡帆船港</a>                                                </dd>        </dl>            </li>    <li class=\"item \" >        <div class=\"img\">            <a href=\"/travel-scenic-spot/mafengwo/146835.html\" target=\"_blank\" data-type=\"目的地\"  data-id=\"146835\">                <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAADIBAMAAABrKiWYAAAAElBMVEX88tz8+uz89uz8/vz88uT8+vT1Qw6wAAABOUlEQVR4nO3ZUU7CQBRA0crACtwI+nQFbsDE/e9FodCWMhDNJMyLOeeDhOnPzStkmukwAAAAAAAAAAAAAADw/0TE1VqJ2HdIqYq4LiyHtSSFX1GJidpiJ2PL5QhLosBTy2VM1Ko7ObaVy5jxa5IRjmk/n6/T0mZMe8oSeKgoy3F9nOaZKXBY/OKm2FSBixFOrakC56w5NVfgJq5udq7A444yjAP8XF3qaq4YRxjLhVyBZb1/ZAs8b3D7yqWOFhVltQGnCxxWTw35AlfPBwkDf33pgQS2EthKYCuBrQS2EthKYCuBre4cApYUgbs7Ee+Py4C8tnf+xW8P7Ljp9tuGPKf8tcRtmjc55zO3eJnXnmN90NXRdCpY0zvuaJe8b7g5xM/eXUt5pzcrSWcHAAAAAAAAAAAAAAD8xTfJkmREsuth7AAAAABJRU5ErkJggg==\" data-original=\"http://n4-q.mafengwo.net/s7/M00/F7/BA/wKgB6lRlkJOAFRstAAF_lQN6FvI94.jpeg?imageMogr2%2Fthumbnail%2F%21320x200r%2Fgravity%2FCenter%2Fcrop%2F%21320x200%2Fquality%2F90\" height=\"200\" width=\"320\">                <div class=\"title\">                    措辛厄岛                    <p class=\"enname\">T?singe</p>                </div>                            </a>        </div>        <dl class=\"caption\">            <dt>                <div class=\"nums\">                    <b>1445</b>人去过                </div>                <div class=\"detail\">                    措辛厄岛是丹麦的岛屿，位于菲英岛以南波罗的海，也是个历史古镇，因其建筑而远近闻名，该岛上的城堡历史悠久，有丰富的历史背景，大都建于1639年。岛上风景秀丽，日照时间长。来这里度假的人更愿意享受它千年古镇的宁静和沐浴在它温暖的阳光下。                </div>            </dt>            <dd>                            </dd>        </dl>            </li>\",\"page\":\"<div align=\"right\" class=\"m-pagination\">                    <span class=\"count\">共9页</span>                <a rel=\"nofollow\" data-page=\"1\" href=\"#\" class=\"pg-prev _j_pageitem\">上一页</a>                            <a rel=\"nofollow\" data-page=\"1\" href=\"#\" class=\"pi _j_pageitem\">1</a>                                <span class=\"pg-current\">2</span>                                <a rel=\"nofollow\" data-page=\"3\" href=\"#\" class=\"pi _j_pageitem\">3</a>                                <a rel=\"nofollow\" data-page=\"4\" href=\"#\" class=\"pi _j_pageitem\">4</a>                                <a rel=\"nofollow\" data-page=\"5\" href=\"#\" class=\"pi _j_pageitem\">5</a>                                <a rel=\"nofollow\" data-page=\"6\" href=\"#\" class=\"pi _j_pageitem\">6</a>                        <a rel=\"nofollow\" data-page=\"3\" href=\"#\" class=\"pg-next _j_pageitem\">后一页</a>        <a rel=\"nofollow\" data-page=\"9\" href=\"#\" class=\"pg-last _j_pageitem\">末页</a>    </div>\n";

        Parser parser = new Parser();
        List<CityBean> list = parser.parseCity(html);
        for(CityBean city: list){
            System.out.println(city.getChineseName());
            System.out.println(city.getEnglishName());
            System.out.println(city.getNumVisitor());
        }
    }
}