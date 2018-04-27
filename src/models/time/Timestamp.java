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
        this.timestamp = timestamp;
    }
    
    /**
     * 根据当前系统时间初始化
     */
    public Timestamp() {
        this(System.currentTimeMillis());
    }
    
    /**
     * 获取时间戳long值
     *
     * @return long值
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * 获取移动后的时间戳
     *
     * @param offset 位移量
     * @return 移动后的时间戳
     */
    public Timestamp getOffseted(long offset) {
        return new Timestamp(this.timestamp + offset);
    }
    
    /**
     * 获取向前对齐时间戳
     *
     * @param unit 对其单元（单位：ms）
     * @return 向前对齐后的时间戳
     */
    public Timestamp getForwardAligned(long unit) {
        return new Timestamp(this.timestamp - this.timestamp % unit);
    }
    
    /**
     * 获取向后对齐时间戳
     *
     * @param unit 对齐单元（单位：ms）
     * @return 向后对齐后的时间戳
     */
    public Timestamp getBackwardAligned(long unit) {
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
        return Long.compare(this.timestamp, timestamp.timestamp);
    }
    
    /**
     * 时间戳哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return this.timestamp.hashCode();
    }
}
