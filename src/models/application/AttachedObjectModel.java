package models.application;

import interfaces.application.AttachedObjectInterface;
import models.parse.ApplicationParser;

/**
 * 附加值模型
 * <p>
 * 特点：
 * 1、自带附加值功能和封装
 * 2、自带public方法访问附加值
 * 3、自带protected方法修改附加值
 *
 * @param <T> 附加对象类型
 */
public abstract class AttachedObjectModel<T> extends ApplicationParser implements AttachedObjectInterface<T> {
    /**
     * 附加对象
     */
    private T object;
    
    /**
     * 获取附加对象
     *
     * @return 附加对象
     */
    @Override
    public T getAttachedObject() {
        /**
         * @effects:
         *          \result == \this.object;
         */
        return object;
    }
    
    /**
     * 设置附加对象
     *
     * @param object 附加对象
     */
    protected void setAttachedObject(T object) {
        /**
         * @modifies:
         *          \this.object;
         * @effects:
         *          \this.object == object;
         */
        this.object = object;
    }
}
