package models.time;

import models.application.HashBasedModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳对象
 * <p>
 * 特性：
 * 1、对unix时间戳进行封装
 * <p>
 * 用途：
 * 1、封装指定的时间戳
 * 2、自动获取当前时间戳
 * 3、提供toString(),输出标准的时间格式（精确到毫秒）
 * 4、可通过Comparable直接与Timestamp类型比较大小
 */
public class Timestamp extends HashBasedModel implements Comparable<Timestamp> {
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 根据long时间戳初始化
     *
     * @param timestamp long格式时间戳
     */
    public Timestamp(long timestamp) {
        /**
         * @modifies:
         *          \this.timestamp;
         * @effects:
         *          \this.timestamp = timestamp;
         */
        this.timestamp = timestamp;
    }
    
    /**
     * 根据当前系统时间初始化
     */
    public Timestamp() {
        /**
         * @modifies:
         *          \this.timestamp;
         * @effects:
         *          \this.timestamp will be set to the current timestamp;
         */
        this(System.currentTimeMillis());
    }
    
    /**
     * 获取时间戳long值
     *
     * @return long值
     */
    public long getTimestamp() {
        /**
         * @effects:
         *          \result = \this.timestamp;
         */
        return timestamp;
    }
    
    /**
     * 获取移动后的时间戳
     *
     * @param offset 位移量
     * @return 移动后的时间戳
     */
    public Timestamp getOffseted(long offset) {
        /**
         * @effects:
         *          \result.timestamp = \this.timestamp + offset;
         */
        return new Timestamp(this.timestamp + offset);
    }
    
    /**
     * 获取向前对齐时间戳
     *
     * @param unit 对其单元（单位：ms）
     * @return 向前对齐后的时间戳
     */
    public Timestamp getForwardAligned(long unit) {
        /**
         * @effects:
         *          \result.timestamp = \this.timestamp - \this.timestamp % unit;
         */
        return new Timestamp(this.timestamp - this.timestamp % unit);
    }
    
    /**
     * 获取向后对齐时间戳
     *
     * @param unit 对齐单元（单位：ms）
     * @return 向后对齐后的时间戳
     */
    public Timestamp getBackwardAligned(long unit) {
        /**
         * @effects:
         *          (( \ this.timestamp % unit) == 0) ==> \result.timestamp = \this.timestamp;
         *          ((\this.timestamp % unit) != 0) ==> \result.timestamp = \this.timestamp + (unit - \this.timestamp % unit);
         */
        long remain = this.timestamp % unit;
        long timestamp = this.timestamp;
        if (remain > 0) timestamp += (unit - remain);
        return new Timestamp(timestamp);
    }
    
    /**
     * 获取字符串格式时间戳
     *
     * @return 字符串格式时间戳
     */
    @Override
    public String toString() {
        /**
         * @effects:
         *          \result will be set to the String format of the timestamp(using format yyyy-MM-dd HH:mm:ss.SSS)
         */
        Date d = new Date(this.timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(d);
    }
    
    /**
     * 时间戳大小比对
     *
     * @param timestamp 另一个时间戳对象
     * @return 大小比对结果
     */
    @Override
    public int compareTo(Timestamp timestamp) {
        /**
         * @effects:
         *          \result = \this.timestamp <=> timestamp.timestamp;
         * @notice:
         *          <=> means the compare operation of the values;
         */
        return Long.compare(this.timestamp, timestamp.timestamp);
    }
    
    /**
     * 对象相等性判定
     *
     * @param obj 另一个对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        /**
         * @effects:
         *          (\ this = = obj) ==> \result = true;
         *          (obj is an instance of Timestamp) ==> \result = \this <=> obj;
         *          ((\this != obj) && (obj is not an instance of Timestamp)) ==> \result = false;
         */
        if (this == obj) {
            return true;
        } else if (obj instanceof Timestamp) {
            return Timestamp.compare(this, (Timestamp) obj) == 0;
        } else {
            return false;
        }
    }
    
    /**
     * 时间戳哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        /**
         * @effects:
         *          \result will be set to the hashCode of \this.timestamp;
         */
        return this.timestamp.hashCode();
    }
    
    
    /**
     * 比较两个时间戳对象
     *
     * @param timestamp1 时间戳1
     * @param timestamp2 时间戳2
     * @return 比对结果
     */
    public static int compare(Timestamp timestamp1, Timestamp timestamp2) {
        /**
         * @effects:
         *          \result = timestamp1 <=> timestamp2;
         * @notice:
         *          <=> means the compare operation of the values;
         */
        return timestamp1.compareTo(timestamp2);
    }
}
