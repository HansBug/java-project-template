package events.thread;

import models.thread.ApplicationThread;

/**
 * 线程轮询结束后事件
 */
public class ThreadAfterCirculationEvent extends ApplicationThreadEvent {
    /**
     * 构造函数
     *
     * @param host 事件发生者
     */
    public ThreadAfterCirculationEvent(ApplicationThread host) {
        /**
         * @effects:
         *          it will be initialized by super class;
         */
        super(host);
    }
}
