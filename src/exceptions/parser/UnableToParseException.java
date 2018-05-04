package exceptions.parser;

/**
 * 解析错误类
 * <p>
 * 用途：
 * 1、用于表示静态解析类的解析错误（例如IntegerParser的错误）
 */
public class UnableToParseException extends ParserException {
    /**
     * 构造函数
     *
     * @param str 原字符串
     */
    public UnableToParseException(String message, String str) {
        super(String.format("Unable to parse \"%s\" : %s", str, message), str);
    }
    
}
