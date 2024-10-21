package com.bookstore.backend.service.user;

import java.util.Map;

/**
 * @ClassName LoginService
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/19 22:28
 * @Version V1.0
 **/
public interface LoginService {
    Map<String, String> getToken(String username, String password);
}
