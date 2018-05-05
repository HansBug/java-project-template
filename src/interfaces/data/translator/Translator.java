package interfaces.data.translator;

import interfaces.application.ApplicationInterface;

/**
 * 对象转化接口
 *
 * @param <X> 原数据类型
 * @param <Y> 新数据类型
 */
public interface Translator<X, Y> extends ApplicationInterface {
    /**
     * 对象转化
     *
     * @param origin 原对象
     * @return 转换结果
     */
    Y translate(X origin);
}
