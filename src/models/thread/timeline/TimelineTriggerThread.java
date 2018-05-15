package models.thread.timeline;

import events.thread.ThreadExceptionEvent;
import events.thread.ThreadTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.AbstractTimelineThreadInterface;
import interfaces.thread.TriggerInterface;
import models.structure.object.TimeBasedObject;
import models.thread.circulation.SimpleCirculationThread;
import models.thread.trigger.DelayUntilThread;
import models.thread.trigger.TriggerThread;
import models.time.Timestamp;

import java.util.PriorityQueue;

/**
 * 时间线触发器线程
 * <p>
 * 功能：
 * 1、可以设置多条定时触发任务
 * <p>
 * 特性：
 * 1、由消息队列统一管理，在即将到时间时启动DelayUntilTrigger
 * 2、消息队列为优先队列，性能有保证
 * 3、由于所有任务均为单独线程，故任务内执行时间较长也不会导致后面的任务触发事件受影响，任务执行完毕后该线程自动销毁
 * <p>
 * 注：
 * 1、该模块精度低于DelayThread和DelayUntilThread（精度误差大约在0-10ms之间，不过没有累积误差）
 * 2、该模块适用于大量定时任务排队且精度要求不是很高的情况，可以有效节省资源占用
 * 3、如果为单一且等间隔的任务，推荐使用TimerThread
 *
 * @param <T> 附加类型
 */
public class TimelineTriggerThread<T> extends AbstractTimelineTriggerThread<T, T> {
    
    /**
     * 父类继承
     *
     * @param timestamp       时间戳
     * @param trigger         触发器
     * @param attached_object 附加对象
     */
    @Override
    public void add(Timestamp timestamp, TriggerInterface trigger, T attached_object) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          addTimer the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        super.add(timestamp, trigger, attached_object);
    }
    
    /**
     * 数据直传
     *
     * @param trigger 触发器接口
     * @param data    数据
     * @return 数据
     */
    @Override
    public T getDataCompress(TriggerInterface trigger, T data) {
        /**
         * @effects:
         *          \result == data;
         */
        return data;
    }
    
    /**
     * 数据直传
     *
     * @param pack 数据
     * @return 数据
     */
    @Override
    public T getDataUncompress(T pack) {
        /**
         * @effects:
         *          \result == pack;
         */
        return pack;
    }
    
    /**
     * 新增触发器（在指定的时间戳上触发）
     *
     * @param timestamp 时间戳
     * @param trigger   触发器
     */
    public void add(Timestamp timestamp, TriggerInterface trigger) {
        add(timestamp, trigger, null);
    }
    
    /**
     * 新增触发器（调用后一定时间触发）
     *
     * @param time_after      时间间隔
     * @param trigger         触发器
     * @param attached_object 附加对象
     */
    public void add(long time_after, TriggerInterface trigger, T attached_object) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          calculate a new time based on current time;
         *          addTimer the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            Timestamp timestamp = new Timestamp().getOffseted(time_after);
            this.add(timestamp, trigger, attached_object);
        }
    }
    
    /**
     * 新增触发器（调用后一定时间触发）
     *
     * @param time_after 时间间隔
     * @param trigger    触发器
     */
    public void add(long time_after, TriggerInterface trigger) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          calculate a new time based on current time;
         *          addTimer the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        this.add(time_after, trigger, null);
    }
}
