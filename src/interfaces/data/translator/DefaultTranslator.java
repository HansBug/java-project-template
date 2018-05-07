package interfaces.data.translator;

/**
 * 默认同类转换器
 *
 * @param <X> 数据类型
 */
public interface DefaultTranslator<X> extends Translator<X, X> {
    /**
     * 对象原样转化
     *
     * @param origin 原对象
     * @return 转化结果
     */
    @Override
    default X translate(X origin) {
        /**
         * @effects:
         *          \result = origin;
         */
        return origin;
    }
}
