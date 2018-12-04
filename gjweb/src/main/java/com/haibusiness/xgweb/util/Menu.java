package com.haibusiness.xgweb.util;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    private static final Map<String,String> menus=new HashMap<>();
    public static Map<String,String> getMenu(){

        menus.put("gaikuang","系部概况");
        menus.put("jianjie","学院简介");
        menus.put("shizi","师资队伍");
        menus.put("ditu","校园地图");
        menus.put("dgangjianDao","党建工作");
        menus.put("jiaoyan","教研工作");
        menus.put("jiaoxue","教学活动");
        menus.put("keyan","科研情况");
        menus.put("shenghuo","留学生活");
        menus.put("huodong","留学生活动");
        menus.put("tishi","留学生提示");
        menus.put("jiangxuejin","奖学金");
        menus.put("zhongguo","中国政府");
        menus.put("hainan","海南省");
        menus.put("kongzi","孔子学院");
        menus.put("zhaosheng","招生信息");
        menus.put("zhuanxin","专业信息");
        menus.put("shenqing","申请信息");
        menus.put("xiazai","资料下载");
        menus.put("tongzhi","通知通告");
        menus.put("dongtai","学院动态");
        menus.put("tupian","图片新闻");
        menus.put("biyesheng","优秀毕业生");

     return menus;

    }
}
