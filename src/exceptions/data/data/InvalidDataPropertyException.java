package exceptions.data.data;

import exceptions.data.property.InvalidPropertyException;

/**
 * 数据属性异常
 */
public class InvalidDataPropertyException extends InvalidDataException {
    /**
     * 属性异常异常类
     */
    private final InvalidPropertyException property_exception;
    
    /**
     * 构造函数
     *
     * @param data               非法原数据
     * @param property_exception 数据异常类
     */
    public InvalidDataPropertyException(Object data, InvalidPropertyException property_exception) {
        /**
         * @modifies:
         *          \this.data;
         *          \this.property_exception;
         *
         * @effects:
         *          message will be set using the constructor of the parent class and the value of property_exception.getMessage();
         *          \this.data = data;
         *          \this.property_exception = property_exception;
         */
        super(data, property_exception.getMessage());
        this.property_exception = property_exception;
    }
    
    /**
     * 获取数据异常类
     *
     * @return 数据异常类
     */
    public InvalidPropertyException getPropertyException() {
        /**
         * @effects:
         *          \result = \this.property_exception;
         */
        return property_exception;
    }
}
