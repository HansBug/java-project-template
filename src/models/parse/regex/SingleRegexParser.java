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
     * @param regex_expression 正则表达式
     */
    public SingleRegexParser(String regex_expression) {
        this.pattern = Pattern.compile(regex_expression);
    }
    
    /**
     * 获取正则表达式
     *
     * @return 正则表达式
     */
    public String getRegularExpression() {
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
