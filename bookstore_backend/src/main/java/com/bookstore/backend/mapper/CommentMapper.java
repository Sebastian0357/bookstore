package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CommentMapper
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 23:08
 * @Version V1.0
 **/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentsByBookId(@Param("bookId") Integer bookId);

    IPage pageC(IPage<Comment> page);

    IPage pageCC(IPage<Comment> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
