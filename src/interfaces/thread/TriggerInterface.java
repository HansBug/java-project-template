package interfaces.thread;

import events.thread.ThreadTriggerEvent;
import interfaces.application.ApplicationInterface;

/**
 * 触发器线程接口
 */
public interface TriggerInterface extends ApplicationInterface {
    /**
     * 触发器事件
     *
     * @param e 事件对象
     */
    void trigger(ThreadTriggerEvent e);
}
