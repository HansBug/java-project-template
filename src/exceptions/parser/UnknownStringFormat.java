package exceptions.parser;

/**
 * 字符串格式无法匹配正则
 */
public class UnknownStringFormat extends ParserException {
    /**
     * 构造函数
     *
     * @param str 原字符串
     */
    public UnknownStringFormat(String str) {
        super(String.format("Unknown string format - \"%s\".", str), str);
    }
}
