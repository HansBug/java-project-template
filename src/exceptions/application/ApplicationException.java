package exceptions.application;

import interfaces.application.ApplicationInterface;

/**
 * 异常类基类
 * 通过继承此类，可用于较好的区分人工抛出的异常和java本身的异常
 */
public abstract class ApplicationException extends Exception implements ApplicationInterface {
    /**
     * 根据异常信息初始化构造函数
     *
     * @param message 异常信息
     */
    public ApplicationException(String message) {
        super(message);
    }
    
}
