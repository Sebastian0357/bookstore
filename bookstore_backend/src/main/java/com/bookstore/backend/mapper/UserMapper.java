package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName UserMapper
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/9/29 22:44
 * @Version V1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 分页查询：根据条件查询
    IPage pageCC(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}

