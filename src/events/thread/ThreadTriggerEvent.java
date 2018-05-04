package events.thread;

import models.thread.ApplicationThread;
import models.time.Timestamp;

/**
 * 线程触发器事件对象
 */
public class ThreadTriggerEvent extends ApplicationThreadEvent {
    /**
     * 触发时间
     */
    private final Timestamp target_timestamp;
    
    /**
     * 获取触发事件戳
     *
     * @return 触发时间戳
     */
    public Timestamp getTargetTimestamp() {
        return this.target_timestamp;
    }
    
    /**
     * 构造函数
     *
     * @param host              事件发生者
     * @param target_timestamp 时间戳
     */
    public ThreadTriggerEvent(ApplicationThread host, Timestamp target_timestamp) {
        super(host);
        this.target_timestamp = target_timestamp;
    }
    
    /**
     * 构造函数（自动获取当前时间）
     *
     * @param host 事件发生者
     */
    public ThreadTriggerEvent(ApplicationThread host) {
        super(host);
        this.target_timestamp = new Timestamp();
    }
}
