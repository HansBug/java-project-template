package models.thread.circulation;

import interfaces.thread.NotifyCirculationThreadInterface;
import models.thread.ApplicationThread;

/**
 * 支持题型功能的轮询型线程
 * <p>
 * 特性：
 * 1、提供轮询机制
 * 2、支持自定义阻塞和恢复
 * 3、支持自定义循环退出条件
 * 3、支持自定义循环开始前结束后程序
 */
public abstract class NotifyCirculationThread extends ApplicationThread implements NotifyCirculationThreadInterface {
    /**
     * 阻塞对象
     */
    private final Object wait_object = new Object();
    
    /**
     * 执行线程
     */
    @Override
    public void execute() throws Throwable {
        /**
         * @effects:
         *          Firstly, \this.beforeCirculation will be executed before the circulation begin;
         *          Then, this circulation will start;
         *          After the circulation was broken, \this.afterCirculation will be executed;
         */
        this.beforeCirculation();
        while (this.permitContinue()) {
            if (!this.permitCirculation()) {
                synchronized (this.wait_object) {
                    this.wait_object.wait();
                }
            }
            this.circulation();
        }
        this.afterCirculation();
    }
    
    /**
     * 停止对象阻塞继续循环
     */
    public void permitCirculationAtOnce() {
        /**
         * @effects:
         *          \this.wait_object will be notifyAll();
         */
        synchronized (this.wait_object) {
            this.wait_object.notifyAll();
        }
    }
}
