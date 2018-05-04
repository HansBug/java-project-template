package interfaces.thread;

import events.thread.ThreadBeforeTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;

/**
 * 触发器线程接口
 *
 * @param <T> 返回值类型
 */
public interface TriggerWithReturnValueInterface<T> extends ApplicationThreadInterface {
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
    void trigger(ThreadTriggerWithReturnValueEvent<T> e) throws Throwable;
}
