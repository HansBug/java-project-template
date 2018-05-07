package models.data.property;

import exceptions.data.property.InvalidPropertyException;
import interfaces.data.validator.PropertyValidator;
import models.application.ApplicationModel;

/**
 * 数据字段验证模型
 * <p>
 * 用途：
 * 1、读写数据字段
 * 2、提供自定义数据验证功能
 * 3、支持多个属性验证接口合并为一个数据验证模块
 * <p>
 * 建议：
 * 1、可以直接实例化抽象类实现自定义数据验证
 * 2、可以继承此类实现数据验证逻辑封装
 * 3、如有需要可以重写getErrorMessage方法，订制错误信息
 *
 * @param <T> 字段类型
 */
public abstract class PropertyModel<T> extends ApplicationModel implements PropertyValidator<T> {
    /**
     * 数据存储
     */
    protected T data;
    
    /**
     * 非法数据异常类
     */
    private InvalidPropertyException property_exception = null;
    
    /**
     * 构造函数
     *
     * @param data 初值
     */
    public PropertyModel(T data) {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = data;
         */
        this.data = data;
    }
    
    /**
     * 构造函数（设置初值为null）
     */
    public PropertyModel() {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = null;
         */
        this(null);
    }
    
    /**
     * 数据验证（直接使用自身的数据）
     *
     * @throws InvalidPropertyException 非法数据异常类
     */
    public void validate() throws InvalidPropertyException {
        /**
         * @effects:
         *          normal behavior:
         *              \this.data check by \this.validate(T data);
         *
         *          exceptional behavior(InvalidPropertyException):
         *              InvalidPropertyException throw by \this.validate(\this.data);
         */
        this.validate(this.data);
    }
    
    /**
     * 外部数据合法性判定
     *
     * @param data 外部原数据
     * @return 数据是否合法
     */
    public boolean isValid(T data) {
        /**
         * @modifies:
         *          \this.property_exception;
         * @effects:
         *          normal behavior:
         *              data check by \this.validate(T data);
         *              \this.property_exception = null;
         *              \result = true;
         *
         *          InvalidPropertyException thrown by \this.validate(\this.data):
         *              \this.property_exception = InvalidPropertyException;
         *              \result = false;
         */
        try {
            this.validate(data);
            this.property_exception = null;
            return true;
        } catch (InvalidPropertyException e) {
            this.property_exception = e;
            return false;
        }
    }
    
    /**
     * 合法性判定
     *
     * @return 是否合法
     */
    public boolean isValid() {
        /**
         * @effects:
         *      \result = \this.isValid(\this.data);
         */
        return this.isValid(this.data);
    }
    
    
    /**
     * 获取数据信息
     *
     * @return 数据信息
     */
    public T getData() {
        /**
         * @effects:
         *          \result = \this.data;
         */
        return this.data;
    }
    
    /**
     * 设置数据信息
     *
     * @param data 设置数据信息
     */
    public void setData(T data) {
        /**
         * @modifies:
         *          \this.data;
         * @effects:
         *          \this.data = data;
         */
        this.data = data;
    }
    
    /**
     * 获取参数异常类，为最后一次使用isValid函数内抛出的异常，如果数据正常则为null
     *
     * @return 参数异常类
     */
    public InvalidPropertyException getPropertyException() {
        /**
         * @effects:
         *          \result = \this.property_exception;
         */
        return this.property_exception;
    }
    
    /**
     * 复合数据验证模块生成
     *
     * @param data       初始值
     * @param properties 数据验证模型
     * @param <T>        数据类型
     * @return 复合数据模型
     */
    @SuppressWarnings("unchecked")
    public static <T> PropertyModel<T> getModel(T data, PropertyValidator<T>... properties) {
        /**
         * @requies:
         *          (\ all validator ; properties.contains ( validator); validator != null);
         * @effects:
         *          \result is set to a PropertyModel which can validate all the Models in properties;
         */
        return new PropertyModel<T>(data) {
            @Override
            public void validate(T value) throws InvalidPropertyException {
                for (PropertyValidator<T> validator : properties) {
                    validator.validate(value);
                }
            }
        };
    }
    
    /**
     * 复合数据验证模块生成（初始值null）
     *
     * @param properties 数据验证模型
     * @param <T>        数据类型
     * @return 复合数据模型
     */
    public static <T> PropertyModel<T> getModel(PropertyValidator<T>... properties) {
        /**
         * @effects:
         *          \result = getModel(null, properties);
         */
        return getModel(null, properties);
    }
}
