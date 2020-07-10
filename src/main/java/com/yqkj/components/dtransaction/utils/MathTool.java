package com.yqkj.components.dtransaction.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数据计算工具，采用链式编程方式，最后调用build()输出结果
 */
public class MathTool {

    public static final BigDecimal ZERO = BigDecimal.ZERO;

    public static final BigDecimal ONE = BigDecimal.ONE;

    public static final BigDecimal TEN = BigDecimal.TEN;

    public static final BigDecimal HUNDRED = TEN.multiply(TEN);

    public static final BigDecimal THOUSAND = HUNDRED.multiply(TEN);

    /**
     * 创建一个默认为0的MathTool
     * @return
     */
    public static MathTool getInstance(){
        return new MathTool(MathTool.ZERO);
    }

    public static MathTool getInstance(BigDecimal number){
        return new MathTool(number);
    }

    public static MathTool getInstance(String number){
        return new MathTool(number);
    }

    public static MathTool getInstance(int number){
        return new MathTool(number);
    }

    public static MathTool getInstance(double number){
        return new MathTool(number);
    }

    public static MathTool getInstance(long number){
        return new MathTool(number);
    }

    public static MathTool getInstance(float number){
        return new MathTool(number);
    }

    private BigDecimal result = ZERO;

    private MathTool(BigDecimal number){
        result = new BigDecimal(number.toString());
    }

    private MathTool(String number){
        result = new BigDecimal(number);
    }

    private MathTool(int number){
        result = new BigDecimal(number);
    }

    private MathTool(double number){
        result = new BigDecimal(number);
    }

    private MathTool(long number){
        result = new BigDecimal(number);
    }

    private MathTool(float number){
        result = new BigDecimal(number);
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(String number){
        this.result = this.result.add(new BigDecimal(number));
        return this;
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(BigDecimal number){
        this.result = this.result.add(number);
        return this;
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(int number){
        this.result = this.result.add(new BigDecimal((number)));
        return this;
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(long number){
        this.result = this.result.add(new BigDecimal((number)));
        return this;
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(double number){
        this.result = this.result.add(new BigDecimal((number)));
        return this;
    }

    /**
     * 加法
     * @param number
     * @return
     */
    public MathTool add(float number){
        this.result = this.result.add(new BigDecimal((number)));
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(String number){
        this.result = this.result.subtract(new BigDecimal(number));
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(BigDecimal number){
        this.result = this.result.subtract(number);
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(int number){
        this.result = this.result.subtract(new BigDecimal(number));
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(long number){
        this.result = this.result.subtract(new BigDecimal(number));
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(double number){
        this.result = this.result.subtract(new BigDecimal(number));
        return this;
    }

    /**
     * 减法
     * @param number
     * @return
     */
    public MathTool subtract(float number){
        this.result = this.result.subtract(new BigDecimal(number));
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(String number){
        this.result = this.result.multiply(new BigDecimal(number));
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(BigDecimal number){
        this.result = this.result.multiply(number);
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(int number){
        this.result = this.result.multiply(new BigDecimal(number));
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(long number){
        this.result = this.result.multiply(new BigDecimal(number));
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(double number){
        this.result = this.result.multiply(new BigDecimal(number));
        return this;
    }

    /**
     * 乘法
     * @param number
     * @return
     */
    public MathTool multiply(float number){
        this.result = this.result.multiply(new BigDecimal(number));
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(String number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(new BigDecimal(number), scale, roundingMode);
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(BigDecimal number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(number, scale, roundingMode);
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(int number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(new BigDecimal(number), scale, roundingMode);
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(long number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(new BigDecimal(number), scale, roundingMode);
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(double number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(new BigDecimal(number), scale, roundingMode);
        return this;
    }

    /**
     * 除法
     * @param number
     * @return
     */
    public MathTool divide(float number, int scale, RoundingMode roundingMode){
        this.result = this.result.divide(new BigDecimal(number), scale, roundingMode);
        return this;
    }

    public MathTool setScale(int newScale){
        this.result.setScale(newScale);
        return this;
    }

    public MathTool setScale(int newScale, RoundingMode roundingMode){
        this.result.setScale(newScale, roundingMode);
        return this;
    }

    /**
     * 格式化为货币格式，保留小数点后两位，并进行四舍五入，80.00
     * @return
     */
    public MathTool formatCurrency(){
        this.setScale(2, RoundingMode.HALF_UP);
        return this;
    }

    public BigDecimal build(){
        return this.result;
    }
}
