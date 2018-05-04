package interfaces.parser;

import exceptions.parser.ParserException;
import interfaces.application.ApplicationInterface;

/**
 * 解析器接口
 *
 * @param <T> 返回类型
 */
public interface ParserInterface<T> extends ApplicationInterface {
    /**
     * 解析接口
     *
     * @param str 原字符串
     * @return 返回类型
     * @throws ParserException 解析失败类
     */
    T parse(String str) throws ParserException;
}
