package exceptions.handler;

import exceptions.application.ApplicationException;

/**
 * 处理器异常
 */
public abstract class HandlerException extends ApplicationException {
    /**
     * 根据异常信息初始化构造函数
     *
     * @param message 异常信息
     */
    public HandlerException(String message) {
        /**
         * @effects:
         *          message will be set using the constructor of the parent class;
         */
        super(message);
    }
}
