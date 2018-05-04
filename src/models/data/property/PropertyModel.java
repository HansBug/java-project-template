package models.data.property;

import interfaces.data.PropertyValidator;
import models.application.ApplicationModel;

/**
 * 数据字段验证模型
 * <p>
 * 用途：
 * 1、读写数据字段
 * 2、提供自定义数据验证功能
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
     * 数据验证后对外展示的信息
     */
    private String message;
    
    /**
     * 构造函数
     *
     * @param data 初值
     */
    public PropertyModel(T data) {
        this.data = data;
    }
    
    /**
     * 构造函数（设置初值为null）
     */
    public PropertyModel() {
        this(null);
    }
    
    /**
     * 合法性判定
     *
     * @return 是否合法
     */
    public boolean isValid() {
        boolean result = this.validate(this.data);
        if (result) {
            this.message = null;
        } else {
            this.message = String.format("[%s] %s", this.getClass().getSimpleName(), getErrorMessage(this.data));
        }
        return result;
    }
    
    /**
     * 构建错误信息
     *
     * @param data 错误原数据
     * @return 错误信息
     */
    protected String getErrorMessage(T data) {
        return String.format("Property value \"%s\" is invalid.", data);
    }
    
    /**
     * 获取数据信息
     *
     * @return 数据信息
     */
    public T getData() {
        return this.data;
    }
    
    /**
     * 设置数据信息
     *
     * @param data 设置数据信息
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * 获取信息
     * 1、如果最后一次验证为true，返回null
     * 2、如果最后一次验证为false，返回错误信息
     *
     * @return 信息
     */
    public String getMessage() {
        return this.message;
    }
    
}
