package interfaces.thread;

import event.thread.ThreadExceptionEvent;
import interfaces.application.ApplicationInterface;

/**
 * 线程接口
 */
public interface ApplicationThreadInterface extends ApplicationInterface {
    /**
     * 执行方法
     *
     * @throws Throwable 任意异常类
     */
    void execute() throws Throwable;
    
    /**
     * 异常被触发事件
     *
     * @param e 异常被触发事件
     */
    void exceptionCaught(ThreadExceptionEvent e);
}
