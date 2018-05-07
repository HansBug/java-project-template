package models.thread.trigger;

import events.thread.ThreadBeforeTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.TriggerWithReturnValueInterface;
import models.thread.ApplicationThread;

/**
 * 触发器线程
 * <p>
 * 功能：
 * 1、进行判定（beforeTrigger）后触发（trigger）
 * 2、可以通过改变ThreadBeforeTriggerEvent内的AllowTrigger值来决定是否允许执行trigger（默认允许）
 * 3、可以通过改变ThreadTriggerEvent内的ReturnValue设置trigger函数返回值（默认为null）
 * 4、可以从外部通过getReturnValue来获取trigger函数返回值
 *
 * @param <T> 返回值类型
 */
public abstract class TriggerThread<T> extends ApplicationThread implements TriggerWithReturnValueInterface<T> {
    /**
     * 返回值
     */
    private T return_value;
    
    /**
     * 线程运行
     */
    @Override
    public void execute() throws Throwable {
        /**
         * @modifies:
         *          \this.return_value;
         * @effects:
         *          firstly execute \this.beforeTrigger();
         *          trigger is allowed in \this.beforeTrigger() ==> execute \this.trigger() & \this.return_value set to the return value;
         */
        ThreadBeforeTriggerEvent before_trigger_event = new ThreadBeforeTriggerEvent(this);
        this.beforeTrigger(before_trigger_event);
        if (before_trigger_event.getAllowTrigger()) {
            ThreadTriggerWithReturnValueEvent<T> trigger_event = new ThreadTriggerWithReturnValueEvent<>(this);
            this.trigger(trigger_event);
            this.return_value = trigger_event.getReturnValue();
        }
    }
    
    /**
     * 获取返回值
     *
     * @return 返回值
     */
    public T getReturnValue() {
        /**
         * @effects:
         *          \result = \this.return_value;
         */
        return this.return_value;
    }
}
