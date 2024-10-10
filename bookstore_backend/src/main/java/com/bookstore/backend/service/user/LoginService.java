package com.bookstore.backend.service.user;

import com.bookstore.backend.service.impl.user.LoginServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName UserService
 * @Author WuTong w13694105330@163.com
 * @Date 2024/9/29 22:50
 * @Version V1.0
 **/
@Service
public interface LoginService{
    public Map<String, String> geToken(String username, String password);
}
