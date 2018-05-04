package models.data.property;

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
        super(data);
        this.pattern = Pattern.compile(regexp);
    }
    
    /**
     * 构造函数（null初始值）
     *
     * @param regexp 正则表达式
     */
    public RegexPropertyModel(String regexp) {
        this.pattern = Pattern.compile(regexp);
    }
    
    /**
     * 获取字符串格式正则表达式
     *
     * @return 正则表达式
     */
    public String getRegexp() {
        return this.pattern.pattern();
    }
    
    /**
     * 正则数据验证
     *
     * @param value 原数据
     * @return 验证结果
     */
    @Override
    public boolean validate(T value) {
        if (!super.validate(value)) return false;
        return Pattern.matches(this.pattern.pattern(), value.toString());
    }
    
    /**
     * 生成错误信息
     *
     * @param data 错误原数据
     * @return 错误信息
     */
    @Override
    protected String getErrorMessage(T data) {
        return String.format("Property value \"%s\" not match the regular expression \"%s\".", data.toString(), this.getRegexp());
    }
}
