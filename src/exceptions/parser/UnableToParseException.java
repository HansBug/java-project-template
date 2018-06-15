package exceptions.parser;

/**
 * 无法解析异常
 * 表示未被该解析器识别
 */
public class UnableToParseException extends ParserException {
    /**
     * 构造函数
     * @param origin_string 原字符串
     */
    public UnableToParseException(String origin_string) {
        /**
         * @modifies:
         *          \this.message;
         *          \this.origin_string;
         * @effects:
         *          \this.message == "Failed to parse - \"origin_string\"";
         *          \this.origin_string == origin_string;
         */
        super(String.format("Failed to parse - \"%s\"", origin_string), origin_string);
    }
}
