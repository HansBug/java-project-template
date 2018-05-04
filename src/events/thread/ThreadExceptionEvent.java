package events.thread;

import models.thread.ApplicationThread;

/**
 * 线程出现异常事件
 */
public class ThreadExceptionEvent extends ApplicationThreadEvent {
    /**
     * 异常类
     */
    private final Throwable throwable;
    
    /**
     * 构造函数
     *
     * @param host      发生者
     * @param throwable 异常对象
     */
    public ThreadExceptionEvent(ApplicationThread host, Throwable throwable) {
        super(host);
        this.throwable = throwable;
    }
    
    /**
     * 获取异常对象
     *
     * @return 异常对象
     */
    public Throwable getThrowable() {
        return throwable;
    }
}
