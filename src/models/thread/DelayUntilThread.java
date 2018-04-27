package models.thread;

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
        return until_time;
    }
    
    /**
     * 设置目标时间
     *
     * @param until_time 目标时间
     */
    protected void setUntilTime(Timestamp until_time) {
        this.until_time = until_time;
    }
    
    /**
     * 构造函数
     *
     * @param until_time 目标时间
     */
    public DelayUntilThread(Timestamp until_time) {
        setUntilTime(until_time);
    }
    
    /**
     * 等待方法
     *
     * @return true
     * @throws InterruptedException 中断异常
     */
    @Override
    public boolean beforeTrigger() throws InterruptedException {
        sleepUntil(this.until_time);
        return true;
    }
}
