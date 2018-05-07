package exceptions.parser;

import exceptions.application.ApplicationException;

/**
 * 解析失败
 */
public abstract class ParserException extends ApplicationException {
    /**
     * 原字符串
     */
    private final String origin_string;
    
    /**
     * 构造函数
     *
     * @param message       异常信息
     * @param origin_string 原字符串
     */
    public ParserException(String message, String origin_string) {
        /**
         * @modifies:
         *          \this.origin_string;
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.origin_string = origin_string;
         */
        super(message);
        this.origin_string = origin_string;
    }
    
    /**
     * 获取原字符串
     *
     * @return 原字符串
     */
    public String getOriginString() {
        /**
         * @effects:
         *          \result = \this.origin_string;
         */
        return this.origin_string;
    }
}
