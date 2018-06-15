package models.thread;

import events.thread.ThreadExceptionEvent;
import interfaces.application.ApplicationInterface;
import interfaces.application.ConditionCheckInterface;
import models.time.Timestamp;

/**
 * 全局线程基类
 * <p>
 * 特性：
 * 1、提供全局容错机制，出现异常抛出即可，将触发exceptionCaught事件
 * <p>
 * 建议：
 * 1、强烈推荐使用sleepUntil，可以有效避免长时间的累积误差，精度较高（大概每10次才会出现一个肉眼可见的误差）且cpu占用很低（不像一般的while循环等待，在100线程时完全不卡）
 */
public abstract class ApplicationThread extends Thread implements ApplicationInterface {
    /**
     * 调用方法并进行异常捕捉
     */
    @Override
    public void run() {
        /**
         * @effects:
         *          normal behavior:
         *          \this.execute() will be executed;
         *
         *          exceptional behavior(Throwable):
         *          \this.exceptionCaught() will be executed;
         */
        try {
            this.execute();
        } catch (Throwable e) {
            this.exceptionCaught(new ThreadExceptionEvent(this, e));
        }
    }
    
    /**
     * 执行方法
     *
     * @throws Throwable 任意异常类
     */
    public abstract void execute() throws Throwable;
    
    /**
     * 异常被触发事件
     *
     * @param e 异常被触发事件
     */
    public abstract void exceptionCaught(ThreadExceptionEvent e);
    
    /**
     * 等待型sleep时间余量（建议不低于20）
     */
    private static final long SLEEP_UNTIL_TIME_LEFT = 50;
    
    /**
     * 高精度等待型sleep
     * 不大量占用系统资源且能实现高精度（误差约0.1ms不到）
     *
     * @param timestamp 目标时间戳
     * @throws InterruptedException 中断异常
     */
    public static void sleepUntil(Timestamp timestamp) throws InterruptedException {
        /**
         * @effects:
         *          wait until the timestamp arrive;
         */
        long wait_time = timestamp.getTimestamp() - System.currentTimeMillis() - SLEEP_UNTIL_TIME_LEFT;
        if (wait_time < 0) wait_time = 0;
        sleep(wait_time);
        while (System.currentTimeMillis() < timestamp.getTimestamp()) sleep(0, 100);
    }
    
    /**
     * 条件等待型sleep
     *
     * @param check 条件接口
     * @throws InterruptedException 中断异常
     */
    public static void sleepUntilCondition(ConditionCheckInterface check) throws InterruptedException {
        while (!check.checkCondition()) sleep(1);
    }
}
