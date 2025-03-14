package com.bookstore.backend.controller;

import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/book/{bookId}")
    public Result getCommentsByBookId(@PathVariable Integer bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        if (!comments.isEmpty()) {
            return Result.success(comments);
        }
        return Result.fail();
    }
}
