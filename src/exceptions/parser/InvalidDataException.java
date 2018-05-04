package exceptions.parser;

/**
 * 数据非法异常
 */
public class InvalidDataException extends ParserException {
    /**
     * 构造函数
     *
     * @param message 异常信息
     * @param str     原字符串
     */
    public InvalidDataException(String message, String str) {
        super(String.format("Invalid data of \"%s\" : %s", str, message), str);
    }
}
