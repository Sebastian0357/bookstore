package com.bookstore.backend.controller;

import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.mapper.CommentMapper;
import com.bookstore.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CommentController
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/11 23:19
 * @Version V1.0
 **/
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/book/{bookId}")
    public Result getCommentsByBookId(@PathVariable Integer bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        if (!comments.isEmpty()) {
            return Result.success(comments);
        }
        return Result.fail();
    }

    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        try {
            commentMapper.insert(comment);
            return Result.success();
        } catch (Exception e) {
            return Result.fail();
        }
    }
}
