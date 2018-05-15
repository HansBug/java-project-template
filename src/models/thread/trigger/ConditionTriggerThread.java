package models.thread.trigger;

import events.thread.ThreadBeforeTriggerEvent;
import interfaces.application.ConditionCheckInterface;

/**
 * 情况等待线程
 * <p>
 * 用途：
 * 1、自定义条件等待
 * <p>
 * 特性：
 * 1、等待采用轮询模式（1ms为单位）
 * 2、因为是轮序模式，故如果一次checkCondition速度过慢将会影响后面的判定
 * <p>
 * 建议：
 * 1、等待函数尽量简单
 * 2、尽量避免过高并发使用此类
 */
public abstract class ConditionTriggerThread extends TriggerThread implements ConditionCheckInterface {
    /**
     * 情况等待
     *
     * @param e 触发前事件对象
     * @throws InterruptedException 中断异常
     */
    @Override
    public void beforeTrigger(ThreadBeforeTriggerEvent e) throws InterruptedException {
        /**
         * @effects:
         *          sleep until \this.checkCondition == true;
         */
        sleepUntilCondition(this);
    }
}
