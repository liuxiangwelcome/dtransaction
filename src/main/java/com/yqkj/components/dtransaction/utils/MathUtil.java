package com.yqkj.components.dtransaction.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MathUtil {

    private static final int HUNDRED = 100;

    /**
     * 生成随机数
     *
     * @param number
     * @return
     */
    public static String generateRandom(int number) {
        StringBuffer result = new StringBuffer();
        int[] numbers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < number; i++) {
            result.append(numbers[new Random().nextInt(numbers.length)]);
        }
        return result.toString();
    }

    /**
     * 圆转换为分
     * @param list
     * @param columns
     */
    public static void yuanToFen(List list, String[] columns) {
        if (list == null) {
            return;
        }
        for (Object obj : list){
            yuanToFen(obj, columns);
        }
    }

    /**
     * 圆转换为分
     * @param set
     * @param columns
     */
    public static void yuanToFen(Set set, String[] columns) {
        if (set == null) {
            return;
        }
        for (Object obj : set){
            yuanToFen(obj, columns);
        }
    }

    /**
     * 圆转换为分
     *
     * @param obj
     * @param columns
     */
    public static void yuanToFen(Object obj, String[] columns) {
        if (obj == null || columns == null || columns.length == 0) {
            return;
        }
        try {
            List<String> list = Arrays.asList(columns);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (!list.contains(f.getName())) {
                    continue;
                }
                f.setAccessible(true);
                if (f.getType() == BigDecimal.class) {
                    BigDecimal source = (BigDecimal) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).multiply(MathTool.HUNDRED).build());
                    }
                } else if (f.getType() == Integer.class || f.getType() == int.class) {
                    Integer source = (Integer) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).multiply(MathTool.HUNDRED).build().intValue());
                    }
                } else if (f.getType() == Long.class || f.getType() == long.class) {
                    Long source = (Long) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).multiply(MathTool.HUNDRED).build().longValue());
                    }
                } else if (f.getType() == Double.class || f.getType() == double.class) {
                    Double source = (Double) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).multiply(MathTool.HUNDRED).build().doubleValue());
                    }
                } else if (f.getType() == Float.class || f.getType() == float.class) {
                    Float source = (Float) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).multiply(MathTool.HUNDRED).build().floatValue());
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 分转换为圆
     * @param set
     * @param columns
     */
    public static void fenToYuan(List set, String[] columns) {
        if (set == null) {
            return;
        }
        for (Object obj : set){
            fenToYuan(obj, columns);
        }
    }

    /**
     * 分转换为圆
     * @param set
     * @param columns
     */
    public static void fenToYuan(Set set, String[] columns) {
        if (set == null) {
            return;
        }
        for (Object obj : set){
            fenToYuan(obj, columns);
        }
    }

    /**
     * 分转换为圆
     * 支持
     * BigDecimal
     * Integer      小数点后被截断
     * Long         小数点后被截断
     * Double
     * Float
     *
     * @param obj
     * @param columns
     */
    public static void fenToYuan(Object obj, String[] columns) {
        if (obj == null || columns == null || columns.length == 0) {
            return;
        }
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType() == BigDecimal.class) {
                    BigDecimal source = (BigDecimal) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).divide(HUNDRED, 2, RoundingMode.HALF_UP).build());
                    }
                } else if (f.getType() == Integer.class || f.getType() == int.class) {
                    Integer source = (Integer) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).divide(HUNDRED, 0, RoundingMode.HALF_DOWN).build().intValue());
                    }
                } else if (f.getType() == Long.class || f.getType() == long.class) {
                    Long source = (Long) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).divide(HUNDRED, 0, RoundingMode.HALF_DOWN).build().longValue());
                    }
                } else if (f.getType() == Double.class || f.getType() == double.class) {
                    Double source = (Double) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).divide(HUNDRED, 2, RoundingMode.HALF_UP).build().doubleValue());
                    }
                } else if (f.getType() == Float.class || f.getType() == float.class) {
                    Float source = (Float) f.get(obj);
                    if (source != null) {
                        f.set(obj, MathTool.getInstance(source).divide(HUNDRED, 2, RoundingMode.HALF_UP).build().floatValue());
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
