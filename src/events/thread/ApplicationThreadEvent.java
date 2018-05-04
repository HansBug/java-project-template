package events.thread;

import events.ApplicationEvent;
import models.thread.ApplicationThread;

/**
 * 线程事件
 */
public abstract class ApplicationThreadEvent extends ApplicationEvent<ApplicationThread> {
    /**
     * 线程事件
     * @param host 发生者
     */
    public ApplicationThreadEvent(ApplicationThread host) {
        super(host);
    }
}
