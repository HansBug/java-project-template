package models.input;

import interfaces.data.ModelValidator;
import models.application.TimeBasedModel;

/**
 * 数据模型基类
 * <p>
 * 特性：
 * 1、自带时间戳属性
 * 2、自带数据验证接口，可供数据有效性验证
 * <p>
 * 建议：
 * 1、继承此类来进行输入数据的管理
 */
public abstract class ApplicationInputModel<T> extends TimeBasedModel implements ModelValidator {
}
