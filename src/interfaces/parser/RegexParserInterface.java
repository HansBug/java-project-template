package interfaces.parser;

import exceptions.parser.ParserException;

import java.util.regex.Matcher;

/**
 * 正则解析接口
 *
 * @param <T> 返回类型
 */
public interface RegexParserInterface<T> extends ParserInterface<T> {
    /**
     * 获取解析结果
     *
     * @param matcher 正则匹配对象
     * @param str     原字符串
     * @return 解析结果
     * @throws ParserException 解析失败类
     */
    T getParseResult(Matcher matcher, String str) throws ParserException;
}
