package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookstore.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2024/9/29 22:44
 * @Version V1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
