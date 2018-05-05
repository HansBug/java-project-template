package exceptions.data.property;

/**
 * 参数为null异常
 */
public class NullPropertyException extends InvalidPropertyException {
    /**
     * 构造函数
     *
     * @param data 异常原数据
     */
    public NullPropertyException(Object data) {
        super(data, "Property is null.");
    }
}
