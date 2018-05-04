package models.data.property;

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
        super(data);
    }
    
    /**
     * 构造函数（null初始值）
     */
    public NotNullPropertyModel() {
    }
    
    /**
     * 数据验证
     *
     * @param value 原数据
     * @return 验证结果
     */
    @Override
    public boolean validate(T value) {
        return value != null;
    }
    
    /**
     * 生成错误信息
     *
     * @param data 错误原数据
     * @return 错误信息
     */
    @Override
    protected String getErrorMessage(T data) {
        return "Property value is null.";
    }
}
