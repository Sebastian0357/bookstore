package com.bookstore.backend.controller.book;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName BookInfoController
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:20
 * @Version V1.0
 **/
@RestController
@RequestMapping("/bookinfo")
public class BookInfoController {
    @Autowired
    private BookService bookService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
//        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
//        List<Book> list = bookService.getBookList();
//        Result.success(list);

        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();


        IPage result = bookService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }
}
