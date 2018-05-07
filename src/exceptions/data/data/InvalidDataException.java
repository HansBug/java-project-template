package exceptions.data.data;

import exceptions.application.ApplicationException;

/**
 * 非法数据异常
 */
public abstract class InvalidDataException extends ApplicationException {
    /**
     * 非法的数据
     */
    private final Object data;
    
    /**
     * 构造函数
     *
     * @param data    非法原数据
     * @param message 异常信息
     */
    public InvalidDataException(Object data, String message) {
        /**
         * @modifies:
         *          \this.data;
         *
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.data = data;
         */
        super(message);
        this.data = data;
    }
    
    /**
     * 获取非法原数据
     *
     * @return 非法原数据
     */
    public Object getData() {
        /**
         * @effects:
         *          \result = \this.data;
         */
        return data;
    }
}
