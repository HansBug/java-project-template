package models.application.structure;

import models.application.HashBasedModel;

import java.util.Arrays;

/**
 * 二元对类
 * <p>
 * 功能：
 * 1、存储一个二元对
 * 2、提供哈希判定和相等性判定功能
 *
 * @param <X> 类型X
 * @param <Y> 类型Y
 */
public class GenericPair<X, Y> extends HashBasedModel {
    /**
     * 第一个值
     */
    private X first;
    
    /**
     * 第二个值
     */
    private Y second;
    
    /**
     * 初始化构造函数
     *
     * @param first  第一个值
     * @param second 第二个值
     */
    public GenericPair(X first, Y second) {
        this.first = first;
        this.second = second;
    }
    
    /**
     * 获取第一个值
     *
     * @return 第一个值
     */
    public X getFirst() {
        return first;
    }
    
    /**
     * 设置第一个值
     *
     * @param first 第一个值
     */
    public void setFirst(X first) {
        this.first = first;
    }
    
    /**
     * 获取第二个值
     *
     * @return 第二个值
     */
    public Y getSecond() {
        return second;
    }
    
    /**
     * 设置第二个值
     *
     * @param second 第二个值
     */
    public void setSecond(Y second) {
        this.second = second;
    }
    
    /**
     * 根据双值求哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.first, this.second});
    }
}
