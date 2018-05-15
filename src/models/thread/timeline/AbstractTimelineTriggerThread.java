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
 * 抽象时间线类
 *
 * @param <T> 传入类型
 * @param <K> 内部交互类型
 */
public abstract class AbstractTimelineTriggerThread<T, K> extends SimpleCirculationThread implements AbstractTimelineThreadInterface<T, K> {
    /**
     * 指向自己
     */
    private final AbstractTimelineTriggerThread<T, K> self = this;
    
    /**
     * 触发器触发前执行
     *
     * @param e       事件
     * @param trigger 触发器
     */
    protected void beforeTrigger(ThreadTriggerEvent<K> e, TriggerInterface trigger) {
        /**
         * @effects:
         *          None;
         */
    }
    
    /**
     * 触发器调用
     *
     * @param e       事件
     * @param trigger 触发器
     */
    protected void trigger(ThreadTriggerEvent<K> e, TriggerInterface trigger) {
        /**
         * @effects:
         *          trigger will be called;
         */
        trigger.trigger(new ThreadTriggerEvent<>(e.getHost(), e.getTargetTimestamp(), getDataUncompress(e.getAttachedObject())));
    }
    
    /**
     * 触发器触发后执行
     *
     * @param e       事件
     * @param trigger 触发器
     */
    protected void afterTrigger(ThreadTriggerEvent<K> e, TriggerInterface trigger) {
        /**
         * @effects:
         *          None;
         */
    }
    
    /**
     * 时间戳触发器接口
     */
    protected class TimeBasedTrigger extends TimeBasedObject<TriggerInterface> {
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
        public TimeBasedTrigger(TriggerInterface trigger, Timestamp timestamp, K attached_object) {
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
                /**
                 * 触发器
                 * @param e 触发事件对象
                 * @throws Throwable 任意异常类
                 */
                @Override
                public void trigger(ThreadTriggerWithReturnValueEvent e) throws Throwable {
                    /**
                     * @effects:
                     *          beforeTrigger, trigger, afterTrigger will be called;
                     */
                    ThreadTriggerEvent<K> event = new ThreadTriggerEvent<>(self, timestamp, attached_object);
                    self.beforeTrigger(event, trigger);
                    self.trigger(event, trigger);
                    self.afterTrigger(event, trigger);
                }
                
                /**
                 * 异常处理
                 * @param e 异常被触发事件
                 */
                @Override
                public void exceptionCaught(ThreadExceptionEvent e) {
                    /**
                     * @effects:
                     *          None;
                     */
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
    protected final Object lock_object = new Object();
    
    /**
     * 事件队列
     */
    protected final PriorityQueue<TimeBasedTrigger> queue;
    
    /**
     * 构造函数
     */
    public AbstractTimelineTriggerThread() {
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
     * @param timestamp       时间戳
     * @param trigger         触发器
     * @param attached_object 附加对象
     */
    protected void add(Timestamp timestamp, TriggerInterface trigger, T attached_object) {
        /**
         * @modifies:
         *          \this.queue;
         * @effects:
         *          addTimer the new trigger task into \this.queue;
         *          \this.lock_object will be notified to all;
         */
        synchronized (this.queue) {
            this.queue.add(new TimeBasedTrigger(trigger, timestamp, this.getDataCompress(trigger, attached_object)));
            synchronized (this.lock_object) {
                this.lock_object.notifyAll();
            }
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
