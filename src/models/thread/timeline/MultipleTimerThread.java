package models.thread.timeline;

import events.thread.ThreadTriggerEvent;
import interfaces.thread.TriggerInterface;
import models.time.Timestamp;

/**
 * 多重定时线程
 */
public class MultipleTimerThread<T> extends AbstractTimelineTriggerThread<T, MultipleTimerInformation<T>> {
    
    /**
     * 数据解压
     *
     * @param pack 数据包
     * @return
     */
    @Override
    public T getDataUncompress(MultipleTimerInformation<T> pack) {
        /**
         * @effects:
         *          \result == pack.attached_object;
         */
        return pack.getAttachedObject();
    }
    
    /***
     * 数据压缩（废弃）
     * @param trigger 触发器接口
     * @param data    附加数据
     * @return 压缩结果，均为null
     * @deprecated
     */
    @Override
    public MultipleTimerInformation<T> getDataCompress(TriggerInterface trigger, T data) {
        /**
         * @effects:
         *          \result == null;
         */
        return null;
    }
    
    /**
     * 触发器任务
     *
     * @param e       事件
     * @param trigger 触发器
     */
    @Override
    protected void trigger(ThreadTriggerEvent<MultipleTimerInformation<T>> e, TriggerInterface trigger) {
        /**
         * @effects:
         *          \super.trigger will be called;
         *          this task will be added into \this.queue next time;
         */
        super.trigger(e, trigger);
        this.addTimer(e.getTargetTimestamp(), e.getAttachedObject().getInterval(), trigger, e.getAttachedObject().getAttachedObject());
    }
    
    /**
     * 新增定时器
     *
     * @param interval        时间间隔
     * @param trigger         触发器
     * @param attached_object 附加数据
     */
    public void addTimer(long interval, TriggerInterface trigger, T attached_object) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          add the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        this.addTimer(new Timestamp(), interval, trigger, attached_object);
    }
    
    /**
     * 新增定时器
     *
     * @param timestamp       基础时间
     * @param interval        时间间隔
     * @param trigger         触发器
     * @param attached_object 附加数据
     */
    protected void addTimer(Timestamp timestamp, long interval, TriggerInterface trigger, T attached_object) {
        /**
         * @requires:
         *          timestamp != null;
         * @modifies:
         *          \this.queue;
         * @effects:
         *          add the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            this.queue.add(new TimeBasedTrigger(trigger, timestamp.getOffseted(interval), new MultipleTimerInformation<>(trigger, attached_object, interval)));
            synchronized (this.lock_object) {
                this.lock_object.notifyAll();
            }
        }
    }
}
