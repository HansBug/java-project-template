package models.thread.trigger;

import events.thread.ThreadBeforeTriggerEvent;

/**
 * 延时触发线程
 * <p>
 * 特性：
 * 1、继承TriggerThread
 * <p>
 * 功能：
 * 1、start后等待一定的时间（sleep）后触发trigger
 *
 * @param <T> 返回值类型
 */
public abstract class DelayThread<T> extends TriggerThread<T> {
    /**
     * 等待时间
     */
    private long wait_time;
    
    /**
     * 获取等待时间
     *
     * @return 等待时间
     */
    public long getWaitTime() {
        /**
         * @effects:
         *          \result = \this.wait_time;
         */
        return wait_time;
    }
    
    /**
     * 设置等待时间
     *
     * @param wait_time 等待时间
     */
    protected void setWaitTime(long wait_time) {
        /**
         * @modifies:
         *          \this.wait_time;
         * @effects:
         *          \this.wait_time = wait_time;
         */
        this.wait_time = wait_time;
    }
    
    /**
     * 构造函数
     *
     * @param wait_time 等待时间
     */
    public DelayThread(long wait_time) {
        /**
         * @modifies:
         *          \this.wait_time;
         * @effects:
         *          \this.wait_time = wait_time;
         */
        setWaitTime(wait_time);
    }
    
    /**
     * 延时
     *
     * @param e 触发前事件对象
     * @throws InterruptedException 异常终端
     */
    @Override
    public void beforeTrigger(ThreadBeforeTriggerEvent e) throws InterruptedException {
        /**
         * @effects:
         *          sleep for wait_time milliseconds;
         */
        Thread.sleep(wait_time);
    }
}
