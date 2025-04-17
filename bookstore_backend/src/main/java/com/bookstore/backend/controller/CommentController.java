package com.bookstore.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.mapper.CommentMapper;
import com.bookstore.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentController
 * @Author Kangyx w13694105330@163.com
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
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<Comment> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.apply("b.bookname like {0}", "%" + name + "%");
        }


        IPage result = commentService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("id");
        if (id == null) {
            return Result.fail("缺少 id 参数");
        }
        return commentService.removeById(id) ? Result.success() : Result.fail();
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
