package models.parse.basic;

import exceptions.parser.UnableToParseException;
import interfaces.parser.BasicDataTypeParserInterface;
import models.parse.ApplicationParser;

/**
 * 基础数据类型解析
 *
 * @param <T> 目标类型
 */
public abstract class BasicDataTypeParser<T> extends ApplicationParser<T> implements BasicDataTypeParserInterface<T> {
    /**
     * 基本数据类型解析
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws UnableToParseException 解析失败
     */
    @Override
    public T parse(String str) throws UnableToParseException {
        /**
         * @effects:
         *          normal behavior:
         *              \result = \this.getParseResult(str);
         *          Throwable thrown from \this.getParseResult:
         *              throw UnableToParseException;
         */
        try {
            return this.getParseResult(str);
        } catch (Throwable e) {
            throw new UnableToParseException(e.getMessage(), str);
        }
    }
}
