package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    // 自增的主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    // 书籍名称
    @TableField("bookName")
    private String bookname;
    // 书籍类型
    private int type;
    // 书籍作者
    private String author;
    // 出版社
    private String press;
    // 出版日期
    @TableField("pressDate")
    private Date pressdate;
    // 书籍价格
    private BigDecimal price;
    // 书籍库存数量
    private int count;
    // 书籍封面图片路径
    private String img;
    // 以下是为实现排行榜功能新增的字段
    // 热度，通常用于衡量书籍的受欢迎程度（例如，浏览量、收藏量等）
    // 热度榜按此字段降序排序
    private int popularity; // 热度
    // 销量，衡量书籍的销售情况
    // 畅销榜按此字段降序排序
    private int sales; // 销量
    // 评分，衡量书籍的评价（例如，用户评分）
    // 评分榜按此字段降序排序
    private double rating; // 评分
    // 用于实现最近热搜功能
    private int search; // 搜索次数

    private String description; // 简介
}

