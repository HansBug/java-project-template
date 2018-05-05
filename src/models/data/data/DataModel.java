package models.data.data;

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
     * 注册数据模型
     *
     * @param model 数据模型
     */
    protected void registerModel(PropertyModel model) {
        this.validation_models.add(model);
    }
    
    /**
     * 清空数据模型
     */
    protected void clearModels() {
        this.validation_models.clear();
    }
    
    /**
     * 数据合法性验证
     *
     * @throws InvalidPropertyException 非法属性异常
     */
    @Override
    public void validate() throws InvalidPropertyException {
        for (PropertyModel model : this.validation_models) {
            model.validate();
        }
    }
}
