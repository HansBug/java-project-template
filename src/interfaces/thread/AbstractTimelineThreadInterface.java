package interfaces.thread;

import events.thread.ThreadTriggerEvent;
import interfaces.application.ApplicationInterface;

/**
 * 抽象时间线接口
 *
 * @param <T> 传入数据类型
 * @param <K> 内部运转数据类型
 */
public interface AbstractTimelineThreadInterface<T, K> extends ApplicationInterface {
    /**
     * 数据解打包器
     *
     * @param pack 数据包
     * @return 数据解打包结果
     */
    T getDataUncompress(K pack);
    
    /**
     * 数据打包器
     *
     * @param trigger 触发器接口
     * @param data    附加数据
     * @return 数据打包结果
     */
    K getDataCompress(TriggerInterface trigger, T data);
    
}
