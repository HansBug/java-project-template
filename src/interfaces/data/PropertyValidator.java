package interfaces.data;

import interfaces.application.ApplicationInterface;
import interfaces.application.Translator;

/**
 * 数据项验证接口
 * <p>
 * 用途：
 * 1、用来验证数据项是否合法
 * <p>
 * 建议：
 * 1、在数据类型内部，也可以对各个数据项使用此接口来判定各个数据项的有效性
 *
 * @param <X> 验证类型
 */
public interface PropertyValidator<X> extends ApplicationInterface {
    /**
     * 数据验证
     *
     * @param value 原数据
     * @return 有效性
     */
    boolean validate(X value);
    
    /**
     * 转换为Translator
     *
     * @return Translator
     */
    default Translator<X, Boolean> toValidationTranslator() {
        return new Translator<X, Boolean>() {
            @Override
            public Boolean translate(X origin) {
                return validate(origin);
            }
        };
    }
}
