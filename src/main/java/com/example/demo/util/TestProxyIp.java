package com.example.demo.util;

import java.io.IOException;

public class TestProxyIp  {  
      
    public static void main(String[] args) throws IOException {  
        System.setProperty("http.maxRedirects", "50");  
        System.getProperties().setProperty("proxySet", "true");  
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以  
        String ip = "121.31.176.198";  
        System.getProperties().setProperty("http.proxyHost", ip);  
        System.getProperties().setProperty("http.proxyPort", "8123");  
          
        //确定代理是否设置成功  
       System.out.println(getHtml("http://www.xicidaili.com/"));  
          
    }  
      
    private static String getHtml(String address){  
        String result = null;  
            result = HttpContent.httpUtil(address) ;
        return result;  
    }  
}
