package events;

/**
 * 事件对象
 * <p>
 * 用途：
 * 1、用于支撑面向接口编程时的事件信息传递
 * <p>
 * 建议：
 * 1、继承该类，并加入其他信息，在接口处传递事件信息对象
 *
 * @param <T> 事件发生者类型
 */
public abstract class ApplicationEvent<T> {
    /**
     * 事件发生者
     */
    private T host;
    
    /**
     * 事件构造函数
     *
     * @param host 事件发生者
     */
    public ApplicationEvent(T host) {
        /**
         * @modifies:
         *          \this.host;
         */
        this.host = host;
    }
    
    /**
     * 获取事件发生者
     *
     * @return 事件发生者
     */
    public T getHost() {
        /**
         * @effects:
         *          \result = \this.host;
         */
        return host;
    }
}
