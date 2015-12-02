package com.example.appraise.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by YeahO_O on 11/26/2015.
 */
public class MyWebInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
        webAppContext.register(SpringWebConfig.class);
        //Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("Appraise", new DispatcherServlet(webAppContext));

        // 在启动Tomcat时加载Servlet；否则在处理第一个请求时加载Servlet。
        // PS: 加载需要不少时间（~10秒）
        dispatcher.setLoadOnStartup(1);
        // 符合下面规则的URL，会由Servlet处理。
        dispatcher.addMapping("/");
    }
}
