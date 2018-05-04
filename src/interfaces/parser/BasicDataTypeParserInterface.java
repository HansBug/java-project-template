package interfaces.parser;

/**
 * 基本数据类型解析接口
 *
 * @param <T> 目标类型
 */
public interface BasicDataTypeParserInterface<T> extends ParserInterface<T> {
    /**
     * 获取解析结果
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws Throwable 任意异常
     */
    T getParseResult(String str) throws Throwable;
}
