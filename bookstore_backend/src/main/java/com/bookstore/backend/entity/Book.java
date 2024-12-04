package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Book
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 18:58
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("bookName")
    private String bookname;
    private int type;
    private String author;
    private String press;
    @TableField("pressDate")
    private Date pressdate;
    private BigDecimal price;
    private int count;
    private String img;
}
