package models.time;

import models.application.HashBasedModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳对象
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
