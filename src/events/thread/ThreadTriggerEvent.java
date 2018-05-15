package events.thread;

import models.thread.ApplicationThread;
import models.time.Timestamp;

/**
 * 线程触发器事件对象
 *
 * @param <T> 附加数据段类型
 */
public class ThreadTriggerEvent<T> extends ApplicationThreadEvent {
    /**
     * 触发时间
     */
    private final Timestamp target_timestamp;
    
    /**
     * 附加对象
     */
    private final T attached_object;
    
    /**
     * 获取触发事件戳
     *
     * @return 触发时间戳
     */
    public Timestamp getTargetTimestamp() {
        /**
         * @effects:
         *          \result == \this.target_timestamp;
         */
        return this.target_timestamp;
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
        return this.attached_object;
    }
    
    /**
     * 构造函数（附加对象为null）
     *
     * @param host             事件发生者
     * @param target_timestamp 时间戳
     */
    public ThreadTriggerEvent(ApplicationThread host, Timestamp target_timestamp) {
        /**
         * @modifies:
         *          \this.host;
         *          \this.target_timestamp;
         *          \this.attached_object;
         * @effects:
         *          \this.host == host;
         *          \this.target_timestamp == target_timestamp;
         *          \this.attached_object == null;
         */
        this(host, target_timestamp, null);
    }
    
    /**
     * 构造函数
     *
     * @param host             事件发生者
     * @param target_timestamp 时间戳
     * @param attached_object  附加对象
     */
    public ThreadTriggerEvent(ApplicationThread host, Timestamp target_timestamp, T attached_object) {
        /**
         * @modifies:
         *          \this.host;
         *          \this.target_timestamp;
         *          \this.attached_object;
         * @effects:
         *          \this.host == host;
         *          \this.target_timestamp == target_timestamp;
         *          \this.attached_object == attached_object;
         */
        super(host);
        this.target_timestamp = target_timestamp;
        this.attached_object = attached_object;
    }
    
    
    /**
     * 构造函数（自动获取当前时间）
     *
     * @param host 事件发生者
     */
    public ThreadTriggerEvent(ApplicationThread host) {
        /**
         * @modifies:
         *          \this.host;
         *          \this.target_timestamp;
         *          \this.attached_object;
         * @effects:
         *          \this.host == host;
         *          \this.target_timestamp will be the present time;
         *          \this.attached_object == null;
         */
        this(host, new Timestamp(), null);
    }
}
