package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookstore.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CommentMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/11 23:08
 * @Version V1.0
 **/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentsByBookId(@Param("bookId") Integer bookId);
}
