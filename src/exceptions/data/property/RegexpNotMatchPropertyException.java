package exceptions.data.property;

/**
 * 正则表达式不匹配参数异常
 */
public class RegexpNotMatchPropertyException extends InvalidPropertyException {
    /**
     * 构造函数
     *
     * @param data   异常原数据
     * @param regexp 正则表达式
     */
    public RegexpNotMatchPropertyException(Object data, String regexp) {
        /**
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.data = data;
         */
        super(data, String.format("Property value \"%s\" not match regular expression \"%s\".", data, regexp));
    }
}
