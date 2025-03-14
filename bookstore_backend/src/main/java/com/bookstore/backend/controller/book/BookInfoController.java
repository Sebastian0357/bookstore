package com.bookstore.backend.controller.book;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();


        IPage result = bookService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    // 查询四个排行榜的接口
    @PostMapping("/hotRank")
    public Result getHotRank(@RequestBody QueryPageParam query) {
        // 假设 limit 是动态传入的参数
        int limit = query.getPageSize();  // 你可以根据请求的参数来控制 limit
        List<Book> hotRank = bookService.getHotRank(limit);
        return Result.success(hotRank);
    }

    @PostMapping("/newBookRank")
    public Result getNewBookRank(@RequestBody QueryPageParam query) {
        int limit = query.getPageSize();
        List<Book> newBookRank = bookService.getNewBookRank(limit);
        return Result.success(newBookRank);
    }

    @PostMapping("/bestSellerRank")
    public Result getBestSellerRank(@RequestBody QueryPageParam query) {
        int limit = query.getPageSize();
        List<Book> bestSellerRank = bookService.getBestSellerRank(limit);
        return Result.success(bestSellerRank);
    }

    @PostMapping("/ratingRank")
    public Result getRatingRank(@RequestBody QueryPageParam query) {
        int limit = query.getPageSize();
        List<Book> ratingRank = bookService.getRatingRank(limit);
        return Result.success(ratingRank);
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return Result.success(book);
        }
        return Result.fail();
    }

    @GetMapping("/search")
    public Result searchBooks(@RequestParam("query") String query) {
        if (bookService.searchBooks(query).size() > 0) {
            return Result.success(bookService.searchBooks(query));
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/hotSearch")
    public Result hotSearch(@RequestBody QueryPageParam query) {
        int limit = query.getPageSize();
        List<Book> hotSearch = bookService.hotSearch(limit);
        return Result.success(hotSearch);
    }

    @PostMapping("/increaseSearchCount")
    public Result increaseSearchCount(@RequestBody Map<String, Object> params) {
        Integer bookId = (Integer) params.get("bookId");
        return Result.success(bookService.increaseSearchCount(bookId));
    }
}
