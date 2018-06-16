package models.thread.trigger;

import events.thread.ThreadBeforeTriggerEvent;

/**
 * 立刻触发线程
 *
 * @param <T>
 */
public abstract class TriggerAtOnceThread<T> extends TriggerThread {
    /**
     * 空实现
     *
     * @param e 触发前事件对象
     */
    @Override
    public void beforeTrigger(ThreadBeforeTriggerEvent e) {
    }
}
