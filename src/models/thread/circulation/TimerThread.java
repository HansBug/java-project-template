package models.thread.circulation;

import events.thread.ThreadBeforeTriggerEvent;
import events.thread.ThreadExceptionEvent;
import events.thread.ThreadTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.TriggerInterface;
import models.thread.trigger.TriggerThread;
import models.time.Timestamp;

/**
 * 等间隔定时任务
 * <p>
 * 用途：
 * 1、等间隔定时触发同一任务
 * <p>
 * 特性：
 * 1、系统资源占用低
 * 2、精度较高，且不存在累积误差
 * 3、适合用于等间隔触发的同一定时任务
 * 4、由于每次触发都开启独立线程，故即便运行速度较慢也完全不会影响后续的触发时间
 */
public abstract class TimerThread extends SimpleCirculationThread implements TriggerInterface {
    /**
     * 基础时间戳
     */
    private Timestamp timestamp;
    
    /**
     * 时间间隔
     */
    private long time_span;
    
    /**
     * 对象自身
     */
    private final TimerThread self = this;
    
    /**
     * 构造函数
     *
     * @param time_span 时间间隔
     */
    public TimerThread(long time_span) {
        /**
         * @modifies:
         *          \this.time_span;
         * @effects:
         *          \this.time_span = time_span;
         */
        this.time_span = time_span;
    }
    
    /**
     * 循环开始前初始化
     */
    @Override
    public void beforeCirculation() {
        /**
         * @modifies:
         *          \this.timestamp;
         * @effects:
         *          \this.timestamp will be set to the current time;
         *          trigger for one time using method callTrigger();
         */
        timestamp = new Timestamp();
        callTrigger(timestamp);
    }
    
    /**
     * 循环结束后
     */
    @Override
    public void afterCirculation() {
        /**
         * @effects:
         *          nothing to do when circulation ended;
         */
    }
    
    /**
     * 循环体
     *
     * @throws InterruptedException 中断异常
     */
    @Override
    public void circulation() throws InterruptedException {
        /**
         * @modifies:
         *          \this.timestamp;
         * @effects:
         *          \this.timestamp = \this.timestamp.getOffseted(time_span)
         *          sleep until the new timestamp and call the trigger;
         */
        timestamp = timestamp.getOffseted(time_span);
        sleepUntil(timestamp);
        callTrigger(timestamp);
    }
    
    /**
     * 调用触发器
     *
     * @param timestamp 时间戳
     */
    protected void callTrigger(Timestamp timestamp) {
        /**
         * @effects:
         *          open a new empty TriggerThread to trigger the trigger interface;
         */
        (new TriggerThread() {
            @Override
            public void beforeTrigger(ThreadBeforeTriggerEvent e) {
                /**
                 * @effects:
                 *          nothing to do before trigger;
                 */
            }
            
            @Override
            public void trigger(ThreadTriggerWithReturnValueEvent e) {
                /**
                 * @effects:
                 *          call the method trigger of the self (\this of the outer class);
                 */
                self.trigger(new ThreadTriggerEvent(self, timestamp));
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
                /**
                 * @effects:
                 *          nothing to do when exception throw out of the trigger function;
                 */
            }
        }).start();
    }
    
    /**
     * 异常捕获
     *
     * @param e 异常被触发事件
     */
    @Override
    public void exceptionCaught(ThreadExceptionEvent e) {
        /**
         * @effects:
         *          print the stack trace of the exception to the stderr;
         */
        e.getThrowable().printStackTrace();
    }
    
}
