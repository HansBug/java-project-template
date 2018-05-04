package models.parse;

import interfaces.parser.ParserInterface;
import models.application.ApplicationModel;

/**
 * 解析器基类
 *
 * @param <T> 返回类型
 */
public abstract class ApplicationParser<T> extends ApplicationModel implements ParserInterface<T> {
}
