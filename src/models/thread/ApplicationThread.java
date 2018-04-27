package models.thread;

import interfaces.thread.ApplicationThreadInterface;
import models.time.Timestamp;

/**
 * 全局线程基类
 * <p>
 * 特性：
 * 1、提供全局容错机制，出现异常抛出即可，将通过printStackTrace展示并存储，且可以通过getThrowable访问异常对象
 * <p>
 * 建议：
 * 1、强烈推荐使用sleepUntil，可以有效避免长时间的累积误差，精度较高（大概每10次才会出现一个肉眼可见的误差）且cpu占用很低（不像一般的while循环等待，在100线程时完全不卡）
 */
public abstract class ApplicationThread extends Thread implements ApplicationThreadInterface {
    /**
     * 异常类
     */
    private Throwable throwable = null;
    
    /**
     * 获取异常类
     *
     * @return 异常类
     */
    public Throwable getThrowable() {
        return throwable;
    }
    
    /**
     * 调用方法并进行异常捕捉
     */
    @Override
    public void run() {
        try {
            this.execute();
        } catch (Throwable e) {
            this.throwable = e;
            e.printStackTrace();
        }
    }
    
    
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
        long wait_time = timestamp.getTimestamp() - System.currentTimeMillis() - SLEEP_UNTIL_TIME_LEFT;
        if (wait_time < 0) wait_time = 0;
        sleep(wait_time);
        while (System.currentTimeMillis() < timestamp.getTimestamp()) sleep(1);
    }
}
