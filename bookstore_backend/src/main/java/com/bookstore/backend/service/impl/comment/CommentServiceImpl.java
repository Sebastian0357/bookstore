package com.bookstore.backend.service.impl.comment;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.mapper.CommentMapper;
import com.bookstore.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CommentService
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 23:09
 * @Version V1.0
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByBookId(Integer bookId) {
        return commentMapper.getCommentsByBookId(bookId);
    }

    @Override
    public IPage pageC(IPage<Comment> page) {
        return commentMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Comment> page, Wrapper wrapper) {
        return commentMapper.pageCC(page, wrapper);
    }


}
