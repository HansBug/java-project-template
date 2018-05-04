package events.thread;

import models.thread.ApplicationThread;

/**
 * 线程触发器事件
 *
 * @param <T> 返回值类型
 */
public class ThreadTriggerWithReturnValueEvent<T> extends ThreadTriggerEvent {
    /**
     * 返回值
     */
    private T return_value = null;
    
    /**
     * 线程触发器事件
     *
     * @param host 发生者
     */
    public ThreadTriggerWithReturnValueEvent(ApplicationThread host) {
        super(host);
    }
    
    /**
     * 获取返回值
     *
     * @return 返回值
     */
    public T getReturnValue() {
        return this.return_value;
    }
    
    /**
     * 设置返回值
     *
     * @param return_value 返回值
     */
    public void setReturnValue(T return_value) {
        this.return_value = return_value;
    }
}
