package models.thread;

import interfaces.thread.TriggerThreadInterface;

/**
 * 触发器线程
 * <p>
 * 功能：
 * 1、进行判定（beforeTrigger）后触发（trigger）
 * 2、判定方法返回false则不会触发直接结束
 * 3、可以通过getReturnValue获取触发器函数返回值（如果不需要直接返回null即可）
 *
 * @param <T> 返回值类型
 */
public abstract class TriggerThread<T> extends ApplicationThread implements TriggerThreadInterface<T> {
    /**
     * 返回值
     */
    private T return_value;
    
    /**
     * 线程运行
     */
    @Override
    public void execute() throws Throwable {
        if (this.beforeTrigger()) {
            this.return_value = this.trigger();
        }
    }
    
    /**
     * 获取返回值
     *
     * @return 返回值
     */
    public T getReturnValue() {
        return this.return_value;
    }
}
