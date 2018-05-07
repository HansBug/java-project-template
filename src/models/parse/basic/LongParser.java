package models.parse.basic;

/**
 * Long解析
 */
public class LongParser extends BasicDataTypeParser<Long> {
    /**
     * 解析
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws Throwable 任意异常类
     */
    @Override
    public Long getParseResult(String str) throws Throwable {
        /**
         * @effects:
         *          \result will be Long parse result of str;
         */
        return Long.parseLong(str);
    }
}
