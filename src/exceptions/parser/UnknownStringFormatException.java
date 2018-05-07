package exceptions.parser;

/**
 * 字符串格式无法匹配正则
 */
public class UnknownStringFormatException extends ParserException {
    /**
     * 构造函数
     *
     * @param origin_string 原字符串
     */
    public UnknownStringFormatException(String origin_string) {
        /**
         * @modifies:
         *          \this.origin_string;
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.origin_string = origin_string;
         */
        super(String.format("Unknown string format - \"%s\".", origin_string), origin_string);
    }
}
