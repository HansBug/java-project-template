package models.data.data;

import exceptions.data.data.InvalidDataException;
import exceptions.data.data.InvalidDataPropertyException;
import exceptions.data.property.InvalidPropertyException;
import interfaces.data.validator.ModelValidator;
import models.application.TimeBasedModel;
import models.data.property.PropertyModel;

import java.util.HashSet;

/**
 * 数据模型基类
 * <p>
 * 特性：
 * 1、自带时间戳属性
 * 2、自带数据验证接口，可供数据有效性验证
 * 3、可自定义注册数据验证器，由基类统一管理
 * <p>
 * 建议：
 * 1、继承此类来进行输入数据的管理
 * 2、子类中数据字段尽量使用数据验证器统一管理，并在初始化的时候进行注册
 */
public abstract class DataModel extends TimeBasedModel implements ModelValidator {
    /**
     * 数据验证模型存储
     */
    private HashSet<PropertyModel> validation_models = new HashSet<>();
    
    /**
     * 异常类
     */
    private InvalidDataException data_exception;
    
    /**
     * 注册数据模型
     *
     * @param model 数据模型
     */
    protected void registerModel(PropertyModel model) {
        /**
         * @modifies:
         *          this.validation_models;
         */
        this.validation_models.add(model);
    }
    
    /**
     * 清空数据模型
     */
    protected void clearModels() {
        /**
         * @modifies:
         *          this.validation_models;
         */
        this.validation_models.clear();
    }
    
    
    /**
     * 数据内部自定义验证
     * 1、用于多字段之间的综合性验证（单字段验证建议使用PropertyModel）
     *
     * @throws InvalidDataException 非法数据异常
     */
    protected void validateSelf() throws InvalidDataException {
    
    }
    
    /**
     * 数据合法性验证
     *
     * @throws InvalidDataPropertyException 非法数据异常
     */
    @Override
    public void validate() throws InvalidDataException {
        /**
         * @effects:
         *          normal behavior:
         *          PropertyModel in this.validation_models checked by their model.validate()
         *          this will be checked bu this.validate();
         *
         *          exceptional behavior(InvalidDataException):
         *          (\exists PropertyModel model; this.validation_models.contains(model); InvalidPropertyException thrown out by model.validate());
         *          this.validateSelf() thrown out exception;
         */
        for (PropertyModel model : this.validation_models) {
            try {
                model.validate();
            } catch (InvalidPropertyException e) {
                throw new InvalidDataPropertyException(this, e);
            }
        }
        this.validateSelf();
    }
    
    /**
     * 数据合法性安全验证
     * 不会抛出异常，只返回true/false，异常类存储在this.exception中（无异常则存储null）
     *
     * @return 数据是否合法
     */
    public boolean isValid() {
        /**
         * @modifies:
         *          \this.data_exception;
         *
         * @effects:
         *          normal behavior:
         *              check this.validate();
         *              /this.data_exception = null;
         *              /result = true;
         *
         *          (InvalidPropertyException thrown out by this.validate()) ==>
         *              /this.data_exception = InvalidPropertyException
         *              /result = false;
         *
         */
        try {
            this.validate();
            this.data_exception = null;
            return true;
        } catch (InvalidDataException e) {
            this.data_exception = e;
            return false;
        }
    }
    
    /**
     * 获取数据异常类
     *
     * @return 数据异常类
     */
    public InvalidDataException getDataException() {
        /**
         * @effects:
         *          \result = \this.data_exception;
         */
        return this.data_exception;
    }
}
