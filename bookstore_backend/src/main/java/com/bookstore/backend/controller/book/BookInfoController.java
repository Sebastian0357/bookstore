package com.bookstore.backend.controller.book;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(Book::getBookname, name);
        }
        IPage result = bookService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    // 查询四个排行榜的接口
    @PostMapping("/hotRank")
    public Result getHotRank(@RequestBody QueryPageParam query) {
        // 假设 limit 是动态传入的参数
        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        IPage hotRank = bookService.getHotRank(page);
        return Result.success(hotRank.getRecords());
    }

    @PostMapping("/newBookRank")
    public Result getNewBookRank(@RequestBody QueryPageParam query) {
        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        IPage newBookRank = bookService.getNewBookRank(page);
        return Result.success(newBookRank.getRecords());
    }

    @PostMapping("/bestSellerRank")
    public Result getBestSellerRank(@RequestBody QueryPageParam query) {
        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        IPage bestSellerRank = bookService.getBestSellerRank(page);
        return Result.success(bestSellerRank.getRecords());
    }

    @PostMapping("/ratingRank")
    public Result getRatingRank(@RequestBody QueryPageParam query) {
        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        IPage ratingRank = bookService.getRatingRank(page);
        return Result.success(ratingRank.getRecords());
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

    /*
     * 新增
     * @RequestBody主要用来接收前端传递给后端的json字符串中的数据的
     */
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        book.setRating(0);
        book.setPopularity(0);
        book.setSales(0);
        book.setSearch(0);
        if (!StringUtils.isNotBlank(book.getImg())) {
            book.setImg("http://localhost:1118/static/image/noimg.png");
        }
        return bookService.save(book) ? Result.success() : Result.fail();
    }

    /*
     * 更新物品
     */
    @PostMapping("/update")
    public Result update(@RequestBody Book book) {
        return bookService.updateById(book) ? Result.success() : Result.fail();
    }

    /*
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("id");
        if (id == null) {
            return Result.fail("缺少 id 参数");
        }
        return bookService.removeById(id) ? Result.success() : Result.fail();
    }
}
