package com.bookstore.backend.controller.user;

import com.bookstore.backend.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName RegisterController
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/20 21:11
 * @Version V1.0
 **/
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/register/")
    public Map<String, String> register(@RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");

        return registerService.register(username, password, confirmedPassword);
    }
}
