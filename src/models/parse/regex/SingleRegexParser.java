package models.parse.regex;

import exceptions.parser.ParserException;
import exceptions.parser.UnknownStringFormatException;
import interfaces.parser.RegexParserInterface;
import models.parse.ApplicationParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单正则解析类
 *
 * @param <T> 返回类型
 */
public abstract class SingleRegexParser<T> extends ApplicationParser<T> implements RegexParserInterface<T> {
    /**
     * 正则表达式
     */
    private final Pattern pattern;
    
    /**
     * 构造函数
     *
     * @param regexp 正则表达式
     */
    public SingleRegexParser(String regexp) {
        /**
         * @modifies:
         *          \this.pattern;
         * @effects:
         *          \this.pattern will be the Regular Expression Pattern Object of regexp;
         */
        this.pattern = Pattern.compile(regexp);
    }
    
    /**
     * 获取正则表达式
     *
     * @return 正则表达式
     */
    protected String getRegularExpression() {
        /**
         * @effects:
         *          \result will be set to the string format regular expression of the \this.pattern;
         */
        return this.pattern.pattern();
    }
    
    /**
     * 单正则解析
     *
     * @param str 原字符串
     * @return 解析结果对象
     * @throws ParserException 解析失败类
     */
    @Override
    public T parse(String str) throws ParserException {
        /**
         * @effects:
         *          normal behavior:
         *              \result = \this.getParseResult;
         *          exceptional behavior(UnknownStringFormatException):
         *              regular expression not match the given str;
         */
        if (Pattern.matches(this.getRegularExpression(), str)) {
            Matcher matcher = this.pattern.matcher(str);
            if (matcher.find()) {
                return this.getParseResult(matcher, str);
            } else {
                throw new UnknownStringFormatException(str);
            }
        } else {
            throw new UnknownStringFormatException(str);
        }
    }
}
