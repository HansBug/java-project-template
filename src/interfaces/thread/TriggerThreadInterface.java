package interfaces.thread;

import event.thread.ThreadBeforeTriggerEvent;
import event.thread.ThreadTriggerEvent;

/**
 * 触发器线程接口
 *
 * @param <T> 返回值类型
 */
public interface TriggerThreadInterface<T> extends ApplicationThreadInterface {
    /**
     * 触发等待
     *
     * @param e 触发前事件对象
     * @throws Throwable 任意异常类
     */
    void beforeTrigger(ThreadBeforeTriggerEvent e) throws Throwable;
    
    /**
     * 触发方法
     *
     * @param e 触发事件对象
     * @throws Throwable 任意异常类
     */
    void trigger(ThreadTriggerEvent<T> e) throws Throwable;
}
