package models.data.property;

import exceptions.data.property.InvalidPropertyException;
import exceptions.data.property.NullPropertyException;

/**
 * 非null数据验证类
 * <p>
 * 用途：
 * 1、对数据段进行非null判定
 *
 * @param <T> 数据类型
 */
public class NotNullPropertyModel<T> extends PropertyModel<T> {
    /**
     * 构造函数
     *
     * @param data 初始值
     */
    public NotNullPropertyModel(T data) {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = data;
         */
        super(data);
    }
    
    /**
     * 构造函数（null初始值）
     */
    public NotNullPropertyModel() {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = null;
         */
        super();
    }
    
    /**
     * 数据验证
     *
     * @param value 原数据
     * @throws InvalidPropertyException 非法数据异常
     */
    @Override
    @SuppressWarnings("all")
    public void validate(T value) throws InvalidPropertyException {
        /**
         * @effects:
         *          normal_behaviour
         *          None
         *
         *          (value == null) ==> exceptional_behaviour(NullPropertyException)
         */
        if (value == null) throw new NullPropertyException(value);
    }
}
