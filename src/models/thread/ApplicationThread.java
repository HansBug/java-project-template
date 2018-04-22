package models.thread;

import interfaces.application.ApplicationInterface;
import models.time.Timestamp;

/**
 * 全局线程基类
 */
public abstract class ApplicationThread extends Thread implements ApplicationInterface {
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
