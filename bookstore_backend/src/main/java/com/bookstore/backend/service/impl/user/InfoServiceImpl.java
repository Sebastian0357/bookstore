package com.bookstore.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.mapper.UserMapper;
import com.bookstore.backend.service.impl.utils.UserDetailsImpl;
import com.bookstore.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InfoServiceImpl
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/8/19 22:59
 * @Version V1.0
 **/
@Service
public class InfoServiceImpl extends ServiceImpl<UserMapper, User> implements InfoService {

    @Autowired
    private UserMapper userMapper;

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
        map.put("roleId", user.getRole());

        return map;
    }

    @Override
    public IPage pageCC(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageCC(page, wrapper);
    }

}
