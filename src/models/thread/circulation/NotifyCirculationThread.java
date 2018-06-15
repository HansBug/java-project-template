package models.thread.circulation;

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
public abstract class NotifyCirculationThread extends ApplicationThread {
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
    
    /**
     * 轮询开始前执行
     *
     * @throws Throwable 异常类
     */
    public abstract void beforeCirculation() throws Throwable;
    
    /**
     * 是否允许继续轮询（false则跳出循环）
     *
     * @return 是否允许继续
     * @throws Throwable 异常类
     */
    public abstract boolean permitContinue() throws Throwable;
    
    /**
     * 是否允许立刻执行下一次轮询（false则进行一次对象阻塞）
     *
     * @return 是否允许立即执行
     * @throws Throwable 异常类
     */
    public abstract boolean permitCirculation() throws Throwable;
    
    /**
     * 轮询体程序
     *
     * @throws Throwable 异常类
     */
    public abstract void circulation() throws Throwable;
    
    /**
     * 轮询结束后执行
     *
     * @throws Throwable 异常类
     */
    public abstract void afterCirculation() throws Throwable;
}
