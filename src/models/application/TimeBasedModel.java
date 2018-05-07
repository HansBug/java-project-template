package models.application;

import interfaces.application.TimeBasedInterface;
import models.time.Timestamp;

/**
 * 基于时间戳的模型
 * <p>
 * 特性：
 * 1、在模型初始化的时候自动获取当前系统时间戳
 * 2、提供protect方法，支持在子类中修改时间戳（如果需要支持外部访问，请overwrite一个public方法）
 * <p>
 * 用途：
 * 1、对于基于时间的模型，可以直接继承，减少工作量
 */
public class TimeBasedModel extends ApplicationModel implements TimeBasedInterface {
    /**
     * 时间戳属性
     */
    private Timestamp timestamp;
    
    /**
     * 构造函数
     */
    public TimeBasedModel() {
        /**
         * @modifies:
         *          \this.timestamp;
         */
        this.timestamp = new Timestamp();
    }
    
    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public Timestamp getTimestamp() {
        /**
         * @effects: \result = \this.timestamp;
         */
        return timestamp;
    }
    
    /**
     * 设置时间戳
     *
     * @param timestamp 时间戳
     */
    protected void setTimestamp(Timestamp timestamp) {
        /**
         * @requires:
         *          timestamp != null;
         * @modifies:
         *          \this.timestamp;
         */
        this.timestamp = timestamp;
    }
}
