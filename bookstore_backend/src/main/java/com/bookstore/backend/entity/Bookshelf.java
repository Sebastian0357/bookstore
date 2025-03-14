package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName BookShelf
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:20
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookshelf {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userid")
    private Integer userId;

    @TableField("bookid")
    private Integer bookId;

    @TableField("jointime")
    private Timestamp joinTime;

}
