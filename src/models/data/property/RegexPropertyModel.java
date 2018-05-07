package models.data.property;

import exceptions.data.property.InvalidPropertyException;
import exceptions.data.property.RegexpNotMatchPropertyException;

import java.util.regex.Pattern;

/**
 * 正则表达式数据验证
 * <p>
 * 用途：
 * 1、用于正则表达式验证（支持非字符串类型，以toString返回值为准）
 * <p>
 * 注：
 * 1、请务必保证正则表达式合法性
 * 2、为了防止栈溢出等异常情况出现，请不要使用规模过大的正则表达式
 *
 * @param <T> 数据类型
 */
public class RegexPropertyModel<T> extends NotNullPropertyModel<T> {
    /**
     * 正则表达式对象
     */
    private final Pattern pattern;
    
    /**
     * 构造函数
     *
     * @param regexp 正则表达式
     * @param data   初始值
     */
    public RegexPropertyModel(String regexp, T data) {
        /**
         * @modifies:
         *          \this.data;
         *          \this.pattern;
         * @effects:
         *          \this.data = data;
         *          \this.pattern = Pattern.compile(regexp);
         */
        super(data);
        this.pattern = Pattern.compile(regexp);
    }
    
    /**
     * 构造函数（null初始值）
     *
     * @param regexp 正则表达式
     */
    public RegexPropertyModel(String regexp) {
        /**
         * @modifies:
         *          \this.data;
         *          \this.pattern;
         * @effects:
         *          \this.data = null;
         *          \this.pattern = Pattern.compile(regexp);
         */
        this.pattern = Pattern.compile(regexp);
    }
    
    /**
     * 获取字符串格式正则表达式
     *
     * @return 正则表达式
     */
    public String getRegexp() {
        /**
         * @effects:
         *          \result = \this.pattern.pattern();
         */
        return this.pattern.pattern();
    }
    
    /**
     * 数据验证
     *
     * @param value 原数据
     * @throws InvalidPropertyException 非法数据异常
     */
    @Override
    public void validate(T value) throws InvalidPropertyException {
        /**
         * @effects:
         *          normal_behaviour:
         *          validated by super.validate(T data)
         *          value.toString checked with this.regexp with regular expression matching
         *
         *          exceptional_behaviour(RegexpNotMatchPropertyException): not matched string
         */
        super.validate(value);
        if (!Pattern.matches(this.getRegexp(), value.toString())) {
            throw new RegexpNotMatchPropertyException(value, this.getRegexp());
        }
    }
}
