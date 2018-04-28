package models.application.structure;

import models.application.TimeBasedModel;
import models.time.Timestamp;

/**
 * 基于时间戳的包裹对象
 * <p>
 * 用途：
 * 1、通过继承该类，快速构建需要带时间戳的对象（例子：HashExpireMap中的TimeBasedKey）
 * 2、支持快速根据时间戳大小进行排序
 *
 * @param <T> 包裹对象类型
 */
public abstract class TimeBasedObject<T> extends TimeBasedModel implements Comparable<TimeBasedObject<T>> {
    /**
     * 包裹对象
     */
    private final T object;
    
    /**
     * 构造函数
     *
     * @param object 包裹对象类型
     */
    public TimeBasedObject(T object) {
        this.object = object;
    }
    
    /**
     * 获取包裹对象（protected）
     *
     * @return 包裹对象
     */
    protected T getObject() {
        return this.object;
    }
    
    /**
     * 按照时间比较大小
     *
     * @param o 另一个事件包裹对象
     * @return 比较结果
     */
    @Override
    public int compareTo(TimeBasedObject<T> o) {
        return Timestamp.compare(this.getTimestamp(), o.getTimestamp());
    }
}
