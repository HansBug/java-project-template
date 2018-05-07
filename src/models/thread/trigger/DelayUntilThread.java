package models.thread.trigger;

import events.thread.ThreadBeforeTriggerEvent;
import models.thread.ApplicationThread;
import models.time.Timestamp;

/**
 * 直到型等待线程
 * <p>
 * 特性：
 * 1、继承TriggerThread
 * <p>
 * 功能：
 * 1、start后在指定的时间戳上触发trigger
 *
 * @param <T> 返回值类型
 */
public abstract class DelayUntilThread<T> extends TriggerThread<T> {
    /**
     * 等待目标时间
     */
    private Timestamp until_time;
    
    /**
     * 获取目标时间
     *
     * @return 目标时间
     */
    public Timestamp getUntilTime() {
        /**
         * @effects:
         *          \result = \this.until_time;
         */
        return until_time;
    }
    
    /**
     * 设置目标时间
     *
     * @param until_time 目标时间
     */
    protected void setUntilTime(Timestamp until_time) {
        /**
         * @modifies:
         *          \this.until_time;
         * @effects:
         *          \this.until_time = until_time;
         */
        this.until_time = until_time;
    }
    
    /**
     * 构造函数
     *
     * @param until_time 目标时间
     */
    public DelayUntilThread(Timestamp until_time) {
        /**
         * @modifies:
         *          \this.until_time;
         * @effects:
         *          \this.until_time = until_time;
         */
        setUntilTime(until_time);
    }
    
    /**
     * 等待方法
     *
     * @param e 触发前事件对象
     * @throws InterruptedException 中断异常
     */
    @Override
    public void beforeTrigger(ThreadBeforeTriggerEvent e) throws InterruptedException {
        /**
         * @effects:
         *          wait until the timestamp of \this.until_time came;
         */
        sleepUntil(this.until_time);
    }
}
