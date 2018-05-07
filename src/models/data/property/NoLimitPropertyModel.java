package models.data.property;

import exceptions.data.property.InvalidPropertyException;

/**
 * 无限制数据验证器
 * <p>
 * 特性：
 * 1、验证结果永远返回true
 *
 * @param <T> 数据类型
 */
public class NoLimitPropertyModel<T> extends PropertyModel<T> {
    /**
     * 构造函数
     *
     * @param data 初始值
     */
    public NoLimitPropertyModel(T data) {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = data;
         */
        super(data);
    }
    
    /**
     * 构造函数（初始值null）
     */
    public NoLimitPropertyModel() {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = null;
         */
        super();
    }
    
    /**
     * 数据验证（空验证）
     *
     * @param value 原数据
     * @throws InvalidPropertyException 非法数据异常（虽然不存在的）
     */
    @Override
    public void validate(T value) throws InvalidPropertyException {
    }
}
