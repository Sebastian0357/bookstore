package com.bookstore.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private Integer bookid;
    private Integer orderid;
    private LocalDateTime createtime;
    private String remark;
    private Integer rating;
    private String reply;

}
