package models.structure.pair;

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
        /**
         * @modifies:
         *          \this.first;
         *          \this.second;
         */
        this.first = first;
        this.second = second;
    }
    
    /**
     * 获取第一个值
     *
     * @return 第一个值
     */
    protected X getFirst() {
        /**
         * @effects:
         *          \result = \this.first;
         */
        return first;
    }
    
    /**
     * 设置第一个值
     *
     * @param first 第一个值
     */
    protected void setFirst(X first) {
        /**
         * @modifies:
         *          \this.first;
         */
        this.first = first;
    }
    
    /**
     * 获取第二个值
     *
     * @return 第二个值
     */
    protected Y getSecond() {
        /**
         * @effects:
         *          \result = \this.second;
         */
        return second;
    }
    
    /**
     * 设置第二个值
     *
     * @param second 第二个值
     */
    protected void setSecond(Y second) {
        /**
         * @modifies:
         *          \this.second;
         */
        this.second = second;
    }
    
    /**
     * 获取反转对象
     *
     * @return 反转对象
     */
    protected GenericPair<Y, X> getReversed() {
        /**
         * @effects:
         *          \result.second = \this.first
         *          \result.first = \this.second
         */
        return new GenericPair<>(this.second, this.first);
    }
    
    /**
     * 根据双值求哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        /**
         * @effects:
         *          \result = Arrays.hashCode
         */
        return Arrays.hashCode(new Object[]{this.first, this.second});
    }
    
    /**
     * 二元对相等性判定
     *
     * @param obj 另一个对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        /**
         * @effects:
         *          (\this == obj) ==> \result = true;
         *          (obj is instance of GenericPair) ==> \result = (\this.first equals obj.first) && (\this.second equals obj.second);
         *          (!(\this == obj) && !(obj is instance of GenericPair)) ==> \result = false;
         */
        if (this == obj) {
            return true;
        } else if (obj instanceof GenericPair) {
            return this.first.equals(((GenericPair) obj).first) && this.second.equals(((GenericPair) obj).second);
        } else {
            return false;
        }
    }
}
