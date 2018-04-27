package interfaces.thread;

/**
 * 触发器线程接口
 *
 * @param <T> 返回值类型
 */
public interface TriggerThreadInterface<T> extends ApplicationThreadInterface {
    /**
     * 触发等待
     *
     * @return 是否需要触发
     * @throws Throwable 任意异常类
     */
    boolean beforeTrigger() throws Throwable;
    
    /**
     * 触发方法
     *
     * @return 返回值
     * @throws Throwable 任意异常类
     */
    T trigger() throws Throwable;
}
