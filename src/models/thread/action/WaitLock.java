package models.thread.action;

import models.thread.ApplicationThreadModel;

/**
 * 等待锁
 *
 * @param <T> 附加对象类型
 */
public class WaitLock<T> extends ApplicationThreadModel {
    /**
     * 是否为锁定状态
     */
    private boolean locked = false;
    
    /**
     * 锁定对象
     */
    private final Object lock_object = new Object();
    
    /**
     * 附加对象
     */
    private final T attached_object;
    
    /**
     * 构造函数
     *
     * @param attached_object 附加对象
     */
    public WaitLock(T attached_object) {
        /**
         * @modifies:
         *          \this.attached_object;
         * @effects:
         *          \this.attached_object == attached_object;
         */
        this.attached_object = attached_object;
    }
    
    /**
     * 构造函数（this.attached_object为null）
     */
    public WaitLock() {
        /**
         * @modifies:
         *          \this.attached_object;
         * @effects:
         *          \this.attached_object == null;
         */
        this(null);
    }
    
    /**
     * 尝试等待
     *
     * @throws InterruptedException 中断异常
     */
    public void tryWait() throws InterruptedException {
        /**
         * @effects:
         *          (\ this.locked) ==> wait until \this.lock_object be notifyAll();
         */
        if (this.locked) {
            this.lock_object.wait();
        }
    }
    
    /**
     * 锁定对象
     */
    public synchronized void lock() {
        /**
         * @modifies:
         *          \this.locked;
         * @effects:
         *          \this.locked == true;
         */
        this.locked = true;
    }
    
    /**
     * 解锁对象
     */
    public synchronized void unlock() {
        /**
         * @modifies:
         *          \this.locked;
         * @effects:
         *          \this.locked == false;
         *          \this.lock_object will be notified all;
         */
        this.locked = false;
        this.lock_object.notifyAll();
    }
    
    /**
     * 获取附加对象
     *
     * @return 附加对象
     */
    public T getAttachedObject() {
        /**
         * @effects:
         *          \result == \this.attached_object;
         */
        return attached_object;
    }
}
