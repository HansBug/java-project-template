package models.parse;

import exceptions.parser.ParserException;
import interfaces.parser.ParserInterface;
import models.application.ApplicationModel;

/**
 * 解析器基类
 * <p>
 * 用法：
 * 1、调用parse方法进行解析
 * <p>
 * 建议：
 * 1、请继承并实例化后进行解析调用
 * 2、对于完全无法识别的指令，请抛出UnableToParseException
 * 3、对于被识别但是存在错误的指令，请抛出ParseFailedException;
 *
 * @param <T> 返回类型
 */
public abstract class ApplicationParser<T> extends ApplicationModel implements ParserInterface<T> {
    /**
     * 解析接口
     *
     * @param str 原字符串
     * @return 返回类型
     * @throws ParserException 解析失败类
     */
    public abstract T parse(String str) throws ParserException;
}
