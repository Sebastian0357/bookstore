package com.bookstore.backend.service.user;

import java.util.Map;

/**
 * @ClassName RegisterService
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/8/19 22:28
 * @Version V1.0
 **/
public interface RegisterService {
    Map<String, String> register(String username, String password, String confirmedPassword);
}
