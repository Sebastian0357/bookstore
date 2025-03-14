package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Bookshelf;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName BookShelfMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:23
 * @Version V1.0
 **/
@Mapper
public interface BookshelfMapper extends BaseMapper<Bookshelf> {
    List<Book> getBooksByUserId(Integer userId);
}
