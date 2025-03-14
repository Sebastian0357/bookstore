package com.bookstore.backend.service.bookshelf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.BookShelf;

/**
 * @ClassName BookShelfService
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:26
 * @Version V1.0
 **/
public interface BookShelfService extends IService<BookShelf>  {
    void addToBookshelf();
}
