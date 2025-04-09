package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @ClassName User
 * @Author WuTong w13694105330@163.com
 * @Date 2024/9/29 22:34
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String phonenumber;
    private LocalDate birthday;
    private String email;
    private String photo;
    private String role;
}
