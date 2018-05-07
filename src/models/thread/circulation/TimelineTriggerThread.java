package models.thread.circulation;

import events.thread.ThreadExceptionEvent;
import events.thread.ThreadTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.TriggerInterface;
import models.structure.object.TimeBasedObject;
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
 */
public class TimelineTriggerThread extends SimpleCirculationThread {
    /**
     * 指向自己
     */
    private final TimelineTriggerThread self = this;
    
    /**
     * 时间戳触发器接口
     */
    private class TimeBasedTrigger extends TimeBasedObject<TriggerInterface> {
        /**
         * 触发器线程对象
         */
        private final TriggerThread thread;
        
        /**
         * 构造函数
         *
         * @param trigger   触发器接口
         * @param timestamp 时间戳
         */
        public TimeBasedTrigger(TriggerInterface trigger, Timestamp timestamp) {
            /**
             * @modifies:
             *          \this.object;
             *          \this.timestamp;
             *          \this.thread;
             * @effects:
             *          \this.object = trigger;
             *          \this.timestamp = timestamp;
             *          \this.thread will be set to the DelayUntilThread of the task and the trigger timestamp;
             */
            super(trigger);
            setTimestamp(timestamp);
            this.thread = new DelayUntilThread(timestamp) {
                @Override
                public void trigger(ThreadTriggerWithReturnValueEvent e) throws Throwable {
                    trigger.trigger(new ThreadTriggerEvent(self, timestamp));
                }
                
                @Override
                public void exceptionCaught(ThreadExceptionEvent e) {
                
                }
            };
        }
        
        /**
         * 启动内部线程
         */
        public void start() {
            /**
             * @effects:
             *          \this.thread will be started;
             */
            this.thread.start();
        }
    }
    
    /**
     * 锁定对象
     */
    private final Object lock_object = new Object();
    
    /**
     * 事件队列
     */
    private final PriorityQueue<TimeBasedTrigger> queue;
    
    /**
     * 构造函数
     */
    public TimelineTriggerThread() {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          \this.queue will be initialized to a new PriorityQueue;
         */
        this.queue = new PriorityQueue<>();
    }
    
    /**
     * 新增触发器（在指定的时间戳上触发）
     *
     * @param timestamp 时间戳
     * @param trigger   触发器
     */
    public void add(Timestamp timestamp, TriggerInterface trigger) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          add the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            this.queue.add(new TimeBasedTrigger(trigger, timestamp));
            synchronized (this.lock_object) {
                this.lock_object.notifyAll();
            }
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
         *          add the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            Timestamp timestamp = new Timestamp().getOffseted(time_after);
            this.add(timestamp, trigger);
        }
    }
    
    /**
     * 清空触发器
     */
    public void clear() {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          \this.queue will be cleared;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            this.queue.clear();
            synchronized (this.lock_object) {
                this.lock_object.notifyAll();
            }
        }
    }
    
    /**
     * 轮询开始前
     */
    @Override
    public void afterCirculation() {
        /**
         * @effects:
         *          nothing to do after the circulation;
         */
    }
    
    /**
     * 轮询结束后
     */
    @Override
    public void beforeCirculation() {
        /**
         * @effects:
         *          nothing to do before the circulation;
         */
    }
    
    /**
     * 异常处理
     *
     * @param e 异常被触发事件
     */
    @Override
    public void exceptionCaught(ThreadExceptionEvent e) {
        /**
         * @effects:
         *          nothing to do when exception caught;
         */
    }
    
    /**
     * 提前检测时间
     * 用于提前引发DelayUntilTrigger，尽量不要低于15，且出于性能考虑不要过高
     */
    private static final int PRE_OFFSET_TIME = 25;
    
    /**
     * 轮询
     *
     * @throws Throwable 任意异常类
     */
    @Override
    public void circulation() throws Throwable {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          calculate the valid offset time;
         *          (\this.queue is empty) ==> \this.lock_object start wait;
         *          put all the trigger task that will be trigger in this round into the waiting mode and put them out of \this.queue;
         */
        Timestamp timestamp = new Timestamp().getOffseted(PRE_OFFSET_TIME);
        if (this.queue.isEmpty()) {
            synchronized (this.lock_object) {
                this.lock_object.wait();
            }
        }
        synchronized (this.queue) {
            while (!this.queue.isEmpty()) {
                TimeBasedTrigger trigger = this.queue.peek();
                if (trigger == null) continue;
                if (trigger.getTimestamp().compareTo(timestamp) > 0) break;
                trigger.start();
                this.queue.poll();
            }
        }
        sleepUntil(new Timestamp().getOffseted(1));
    }
    
    /**
     * 退出线程
     */
    @Override
    public void exitGracefully() {
        /**
         * @effects:
         *          \super.exitGracefully will be executed;
         *          Then, the lock will be notified to all;
         */
        super.exitGracefully();
        synchronized (this.lock_object) {
            this.lock_object.notifyAll();
        }
    }
}
