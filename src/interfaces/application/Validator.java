package interfaces.application;

import interfaces.application.Translator;

/**
 * 数据验证接口
 *
 * @param <X>
 */
public interface Validator<X> {
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
     *
     * @return Translator
     */
    default Translator<X, Boolean> toTranslator() {
        return new Translator<X, Boolean>() {
            @Override
            public Boolean translate(X origin) {
                return validate(origin);
            }
        };
    }
}
