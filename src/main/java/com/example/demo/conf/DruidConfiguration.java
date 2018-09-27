package com.example.demo.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

  private static final Logger log = LogManager.getLogger(DruidConfiguration.class);
//  http://localhost:8080/druid/sql.html   访问地址
  @Bean
  public ServletRegistrationBean<StatViewServlet> druidServlet() {
    log.info("init Druid Servlet Configuration ");
    ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
    servletRegistrationBean.setServlet(new StatViewServlet());
    servletRegistrationBean.addUrlMappings("/druid/*");
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("loginUsername", "admin");// 用户名
    initParameters.put("loginPassword", "admin");// 密码
    initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
    initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
    //initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
    servletRegistrationBean.setInitParameters(initParameters);
    return servletRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
    FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }

}

