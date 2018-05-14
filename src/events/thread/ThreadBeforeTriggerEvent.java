package events.thread;

import models.thread.ApplicationThread;

/**
 * 触发器触发前预判事件
 */
public class ThreadBeforeTriggerEvent extends ApplicationThreadEvent {
    /**
     * 允许触发
     */
    private boolean allow_trigger = true;
    
    /**
     * 构造函数
     *
     * @param host 发生者
     */
    public ThreadBeforeTriggerEvent(ApplicationThread host) {
        /**
         * @effects:
         *          it will be initialized by super class;
         */
        super(host);
    }
    
    /**
     * 获取是否允许触发
     *
     * @return 是否允许触发
     */
    public boolean getAllowTrigger() {
        /**
         * @effects:
         *          \result == \this.allow_trigger;
         */
        return this.allow_trigger;
    }
    
    /**
     * 设置是否允许触发
     *
     * @param allow_trigger 是否允许触发
     */
    public void setAllowTrigger(boolean allow_trigger) {
        /**
         * @modifies:
         *          \this.allow_trigger;
         * @effects:
         *          \this.allow_trigger == allow_trigger;
         */
        this.allow_trigger = allow_trigger;
    }
}
