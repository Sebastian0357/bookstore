package com.hotel.common;

import lombok.Data;

import java.util.HashMap;

/**
 * 分页参数的封装类
* @ClassName QueryPageParam.java
* @Author WuTong w13694105330@163.com
* @Date 2024/6/20 17:44
* @Version V1.0
**/
@Data
public class QueryPageParam {
    //设置默认值
    private static int PAGE_SIZE=20;
    private static int PAGE_NUM=1;

    private int pageSize=PAGE_SIZE;
    private int pageNum=PAGE_NUM;

    private HashMap param = new HashMap();

}
