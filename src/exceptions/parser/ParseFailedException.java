package exceptions.parser;

/**
 * 解析失败异常
 * 表示该字符串被解析器识别，但是存在格式错误或者数据冲突等错误
 */
public abstract class ParseFailedException extends ParserException {
    /**
     * 构造函数
     *
     * @param message       异常信息
     * @param origin_string 原字符串
     */
    public ParseFailedException(String message, String origin_string) {
        /**
         * @modifies:
         *          \this.message;
         *          \this.origin_string;
         * @effects:
         *          \this.message == message;
         *          \this.origin_string == origin_string;
         */
        super(message, origin_string);
    }
}
