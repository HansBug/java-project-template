package models.parse.basic;

import java.math.BigInteger;

/**
 * BigInteger解析
 */
public class BigIntegerParser extends BasicDataTypeParser<BigInteger> {
    /**
     * 解析
     *
     * @param str 原字符串
     * @return 解析结果
     * @throws Throwable 任意异常类
     */
    @Override
    public BigInteger getParseResult(String str) throws Throwable {

        return new BigInteger(str);
    }
}
