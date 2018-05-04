package interfaces.data;

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
     * 数据验证
     *
     * @param value 原数据
     * @return 有效性
     */
    boolean validate(T value);
    
    /**
     * 参数验证器合并
     *
     * @param validator 另一个参数验证器
     * @return 合并结果
     */
    default PropertyValidator<T> merge(PropertyValidator<T> validator) {
        PropertyValidator<T> self = this;
        return new PropertyValidator<T>() {
            @Override
            public boolean validate(T value) {
                return self.validate(value) && validator.validate(value);
            }
        };
    }
}
