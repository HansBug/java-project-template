package exceptions.data.property;

import exceptions.application.ApplicationException;

/**
 * 非法参数异常
 */
public class InvalidPropertyException extends ApplicationException {
    /**
     * 异常原数据
     */
    private final Object data;
    
    /**
     * 构造函数
     *
     * @param data    异常原数据
     * @param message 异常信息
     */
    public InvalidPropertyException(Object data, String message) {
        super(message);
        this.data = data;
    }
    
    /**
     * 获取异常原数据
     *
     * @return 原数据
     */
    public Object getData() {
        return data;
    }
    
}
