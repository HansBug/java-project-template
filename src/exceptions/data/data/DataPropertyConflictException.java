package exceptions.data.data;

/**
 * 数据字段冲突异常
 */
public class DataPropertyConflictException extends InvalidDataException {
    /**
     * 构造函数
     *
     * @param data    非法原数据
     * @param message 异常信息
     */
    public DataPropertyConflictException(Object data, String message) {
        /**
         * @modifies:
         *          \this.data;
         *
         * @effects:
         *          message will be set using the constructor of the parent class;
         *          \this.data = data;
         */
        super(data, message);
    }
}
