package models.thread;

import event.thread.ThreadBeforeTriggerEvent;
import event.thread.ThreadExceptionEvent;
import event.thread.ThreadTriggerEvent;
import event.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.TriggerInterface;
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
        this.time_span = time_span;
    }
    
    /**
     * 循环开始前初始化
     */
    @Override
    public void beforeCirculation() {
        timestamp = new Timestamp();
        callTrigger(timestamp);
    }
    
    /**
     * 循环结束后
     */
    @Override
    public void afterCirculation() {
    
    }
    
    /**
     * 循环体
     *
     * @throws InterruptedException 中断异常
     */
    @Override
    public void circulation() throws InterruptedException {
        timestamp = timestamp.getOffseted(time_span);
        sleepUntil(timestamp);
        callTrigger(timestamp);
    }
    
    /**
     * 调用触发器
     *
     * @param timestamp 时间戳
     */
    private void callTrigger(Timestamp timestamp) {
        (new TriggerThread() {
            @Override
            public void beforeTrigger(ThreadBeforeTriggerEvent e) {
            
            }
            
            @Override
            public void trigger(ThreadTriggerWithReturnValueEvent e) {
                self.trigger(new ThreadTriggerEvent(self, timestamp));
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
            
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
        e.getThrowable().printStackTrace();
    }
    
}
