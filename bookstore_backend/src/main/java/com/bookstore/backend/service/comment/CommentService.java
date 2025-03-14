package com.bookstore.backend.service.comment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Comment;

import java.util.List;

/**
 * @ClassName CommentService
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/11 23:09
 * @Version V1.0
 **/
public interface CommentService extends IService<Comment> {
    List<Comment> getCommentsByBookId(Integer bookId);
}
