package models.parse.basic;

/**
 * Double解析
 */
public class DoubleParser extends BasicDataTypeParser<Double> {
    /**
     * 解析
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws Throwable 任意异常类
     */
    @Override
    public Double getParseResult(String str) throws Throwable {
        /**
         * @effects:
         *          \result will be Double parse result of str;
         */
        return Double.parseDouble(str);
    }
}
