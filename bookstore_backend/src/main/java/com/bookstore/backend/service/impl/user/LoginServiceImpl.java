package com.bookstore.backend.service.impl.user;

import com.bookstore.backend.entity.User;
import com.bookstore.backend.service.user.LoginService;
import com.bookstore.backend.service.utils.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2024/9/29 22:49
 * @Version V1.0
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public Map<String, String> geToken(String username, String password) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", user.getPhoto());

        return map;
    }
}
