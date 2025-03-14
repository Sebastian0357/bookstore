package com.bookstore.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.mapper.UserMapper;
import com.bookstore.backend.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RegisterServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/20 20:50
 * @Version V1.0
 **/
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {

        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("message", "用户名不能为空");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("message", "密码不能为空");
            return map;
        }

        username = username.trim();
        if (username.length() == 0) {
            map.put("message", "用户名不能为空");
            return map;
        }

        password = password.trim();
        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("message", "密码不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("message", "用户名长度不能大于100");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("message", "密码长度不能大于100");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("message", "两次输入的密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("message", "用户名已存在");
            return map;
        }

        String encodePassowrd = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/272294_lg_6c5848129b.jpg";
        User user = new User(null, username, encodePassowrd, null, null, null, null, photo, null);
        userMapper.insert(user);

        map.put("message", "register success");
        return map;
    }
}
