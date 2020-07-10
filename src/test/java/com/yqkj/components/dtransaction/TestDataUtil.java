package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.utils.MathTool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataUtil {

    /**
     * 商品库存数量
     */
    public static int STOCK_SIZE = 100;

    /**
     * 用户列表
     */
    public static List<String> users = new ArrayList<>();

    /**
     * 账户列表
     */
    public static Map<String , BigDecimal> accounts = new HashMap<>();

    static {
        users.add("zhangsan");
        users.add("lisi");
        users.add("wangwu");
        users.add("zhaoliu");

        accounts.put("zhangsan", MathTool.getInstance(1000).build());
        accounts.put("lisi", MathTool.getInstance(5000).build());
        accounts.put("wangwu", MathTool.getInstance(200).build());
        accounts.put("zhaoliu", MathTool.getInstance(9800).build());
    }
}
