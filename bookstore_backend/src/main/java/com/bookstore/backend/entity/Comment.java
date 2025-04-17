package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName Comment
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 22:40
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    // 自增的主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    // 用户id
    private int userid;
    private String username;  // 用来存储用户名
    // 书籍id
    private int bookid;
    // 评论
    private String comment;
    // 评论时间
    private Timestamp time;
}
