package models.thread.timeline;

import interfaces.thread.TriggerInterface;
import models.application.ApplicationModel;

/**
 * 多重定时器信息类
 *
 * @param <T> 附加数据类型
 */
class MultipleTimerInformation<T> extends ApplicationModel {
    /**
     * 附加数据
     */
    private final T attached_object;
    
    /**
     * 触发器
     */
    private final TriggerInterface trigger;
    
    /**
     * 时间间隔
     */
    private final long interval;
    
    /**
     * 构造函数
     *
     * @param trigger         触发器
     * @param attached_object 附加对象
     * @param interval        时间间隔
     */
    public MultipleTimerInformation(TriggerInterface trigger, T attached_object, long interval) {
        /**
         * @modifies:
         *          \this.trigger;
         *          \this.attached_object;
         *          \this.interval;
         * @effects:
         *          \this.trigger == trigger;
         *          \this.attached_object == attached_object;
         *          \this.interval == interval;
         */
        this.trigger = trigger;
        this.attached_object = attached_object;
        this.interval = interval;
    }
    
    /**
     * 获取触发器
     *
     * @return 触发器
     */
    public TriggerInterface getTrigger() {
        /**
         * @effects:
         *          \result == \this.trigger;
         */
        return trigger;
    }
    
    /**
     * 获取附加对象
     *
     * @return 获取附加对象
     */
    public T getAttachedObject() {
        /**
         * @effects:
         *          \result == \this.attached_object;
         */
        return attached_object;
    }
    
    /**
     * 获取时间间隔
     *
     * @return 时间间隔
     */
    public long getInterval() {
        /**
         * @effects:
         *          \result == \this.interval;
         */
        return interval;
    }
}
