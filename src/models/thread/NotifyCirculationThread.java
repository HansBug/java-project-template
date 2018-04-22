package models.thread;

import interfaces.thread.NotifyCirculationThreadInterface;

/**
 * 支持题型功能的轮询型线程
 */
public abstract class NotifyCirculationThread extends ApplicationThread implements NotifyCirculationThreadInterface {
    /**
     * 阻塞对象
     */
    private Integer wait_object = 1;
    
    /**
     * 异常捕获
     */
    private Throwable throwable = null;
    
    /**
     * 执行线程
     */
    @Override
    public void run() {
        try {
            this.beforeCirculation();
            while (this.permitContinue()) {
                if (!this.permitCirculation()) {
                    this.wait_object.wait();
                }
                this.circulation();
            }
            this.afterCirculation();
        } catch (Throwable e) {
            this.throwable = e;
            e.printStackTrace();
        }
    }
    
    /**
     * 获取捕获异常对象
     *
     * @return 捕获到的异常对象
     */
    public Throwable getThrowable() {
        return throwable;
    }
    
    /**
     * 停止对象阻塞继续循环
     */
    public void permitCirculationAtOnce() {
        this.wait_object.notifyAll();
    }
}
