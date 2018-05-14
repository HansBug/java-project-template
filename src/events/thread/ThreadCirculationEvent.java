package events.thread;

import models.thread.ApplicationThread;

/**
 * 线程轮询事件
 */
public class ThreadCirculationEvent extends ApplicationThreadEvent {
    /**
     * 构造函数
     *
     * @param host 事件发生者
     */
    public ThreadCirculationEvent(ApplicationThread host) {
        /**
         * @effects:
         *          it will be initialized by super class;
         */
        super(host);
    }
}
