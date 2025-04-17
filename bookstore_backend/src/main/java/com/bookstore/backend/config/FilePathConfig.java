package com.bookstore.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName FilePathConfig
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/12/2 23:19
 * @Version V1.0
 **/
@Configuration
public class FilePathConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/") //虚拟url路径
                .addResourceLocations("file:E:/image/"); //真实本地路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/"); // 这里写你的文件存储路径


    }
}


