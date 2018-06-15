package models.parse;

import exceptions.parser.ParseFailedException;
import exceptions.parser.ParserException;
import exceptions.parser.UnableToParseException;
import interfaces.parser.ParserInterface;

import java.util.ArrayList;

/**
 * 多重解析器
 * <p>
 * 用法：
 * 1、可以快速将多个解析器进行组合，实现一个解析器解析多种请求
 * <p>
 * 特点：
 * 1、逐个解析接口进行尝试
 * 2、遇到解析成功的直接返回结果
 * 3、遇到解析失败（即ParseFailedException，被识别但是存在错误），将异常保留下来，继续循环
 * 4、遇到无法解析（即UnableToParseException），无视并继续循环
 * 5、循环结束后依然未成功的话，如果之前有保留下来的解析解析失败异常，则抛出解析失败异常，否则抛出无法解析异常
 * <p>
 * 建议：
 * 1、请继承并实例化使用，用register方法注册解析接口
 *
 * @param <T> 解析类型
 */
public abstract class MultipleParser<T> extends ApplicationParser<T> {
    /**
     * 解析器列表
     */
    private final ArrayList<ParserInterface<? extends T>> parsers;
    
    /**
     * 构造函数
     */
    public MultipleParser() {
        /**
         * @modifies:
         *          \this.parsers;
         * @effects:
         *          \this.parsers.size() == 0;
         */
        this.parsers = new ArrayList<>();
    }
    
    /**
     * 注册解析器
     *
     * @param parser 解析器接口
     */
    protected void register(ParserInterface<? extends T> parser) {
        /**
         * @modifies:
         *          \this.parsers;
         * @effects:
         *          \new(\this.parsers).size() == \old(\this.parsers).size() + 1;
         *          \new(\this.parsers).contains(parser);
         */
        this.parsers.add(parser);
    }
    
    /**
     * 解析方法
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws ParserException 解析异常
     */
    @Override
    public T parse(String str) throws ParserException {
        /**
         * @effects:
         *          normal behaviour:
         *              \result will be the parse result of the first success result in \this.parsers;
         *
         *          exceptional behaviour(ParseFailedException):
         *              throw ParseFailedException;
         *
         *          exceptional behaviour(other ParserException):
         *              throw UnableToParseException;
         */
        ParseFailedException failedException = null;
        for (ParserInterface<? extends T> parser : this.parsers) {
            try {
                return parser.parse(str);
            } catch (ParserException e) {
                if (e instanceof ParseFailedException) {
                    failedException = (ParseFailedException) e;
                }
            }
        }
        if (failedException == null) {
            throw new UnableToParseException(str);
        } else {
            throw failedException;
        }
        
    }
}
