package com.bookstore.backend.service.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.User;

import java.util.Map;

/**
 * @ClassName InfoService
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/19 22:27
 * @Version V1.0
 **/
public interface InfoService extends IService<User> {
    Map<String, String> getinfo();

    IPage pageCC(IPage<User> page, Wrapper wrapper);//分页查询

}
