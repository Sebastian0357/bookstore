package com.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Record对象", description="")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房间号")
    private Integer id;

    @ApiModelProperty(value = "房间名")
    private String roomname;

    @ApiModelProperty(value = "联系人信息")
    @TableField("userName")
    private String username;

    @ApiModelProperty(value = "价格")
    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "备注")
    private String remark;

////    @TableField(exist = false) 注解加载bean属性上，表示当前属性不是数据库的字段，但在项目中必须使用，
////    这样在新增等使用bean的时候，mybatis-plus就会忽略这个，不会报错
//    @TableField(exist = false)
//    private String action;
}
