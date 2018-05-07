package exceptions.data.property;

/**
 * 数据不在范围内异常
 */
public class OutOfRangePropertyException extends InvalidPropertyException {
    /**
     * 构造函数
     *
     * @param data    异常原数据
     * @param message 异常信息
     */
    public OutOfRangePropertyException(Object data, String message) {
        /**
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.data = data;
         */
        super(data, message);
    }
}
