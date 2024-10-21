package com.bookstore.backend.controller.user;

import com.bookstore.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName InfoController
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/19 23:05
 * @Version V1.0
 **/
@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/user/info/")
    public Map<String, String> getinfo() {
        return infoService.getinfo();
    }
}
