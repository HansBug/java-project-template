package models.parse.basic;

/**
 * Integer解析
 */
public class IntegerParser extends BasicDataTypeParser<Integer> {
    /**
     * 解析
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws Throwable 任意异常类
     */
    @Override
    public Integer getParseResult(String str) throws Throwable {
        /**
         * @effects:
         *          \result will be Integer parse result of str;
         */
        return Integer.parseInt(str);
    }
}
