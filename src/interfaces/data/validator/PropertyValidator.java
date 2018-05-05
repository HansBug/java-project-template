package interfaces.data.validator;

import exceptions.data.property.InvalidPropertyException;
import interfaces.application.ApplicationInterface;

/**
 * 数据项验证接口
 * <p>
 * 用途：
 * 1、用来验证数据项是否合法
 * <p>
 * 建议：
 * 1、在数据类型内部，也可以对各个数据项使用此接口来判定各个数据项的有效性
 *
 * @param <T> 验证类型
 */
public interface PropertyValidator<T> extends ApplicationInterface {
    /**
     * 数据验证（检测到错误需要抛出异常）
     *
     * @param value 原数据
     * @throws InvalidPropertyException 非法参数异常
     */
    void validate(T value) throws InvalidPropertyException;
}
