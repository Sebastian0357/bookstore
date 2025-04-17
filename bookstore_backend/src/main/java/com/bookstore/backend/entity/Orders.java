package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Book
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/12/1 18:58
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;         // 订单ID
    private Integer userid;     // 用户ID
    private Integer bookid;     // 书籍ID
    private String bookname;    // 书籍名称
    private String author;      // 作者
    private BigDecimal price;
    private Date date;          // 订单日期
    private String status;      // 订单状态
}

