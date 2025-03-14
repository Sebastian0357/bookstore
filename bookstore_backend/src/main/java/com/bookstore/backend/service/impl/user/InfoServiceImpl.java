package com.bookstore.backend.service.impl.user;

import com.bookstore.backend.entity.User;
import com.bookstore.backend.service.impl.utils.UserDetailsImpl;
import com.bookstore.backend.service.user.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InfoServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/19 22:59
 * @Version V1.0
 **/
@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getinfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", user.getPhoto());
        map.put("roleid", user.getRole());

        return map;
    }
}
