package interfaces.application;

/**
 * 附加对象接口
 *
 * @param <T> 对象类型
 */
public interface AttachedObjectInterface<T> extends ApplicationInterface {
    /**
     * 获取附加对象
     *
     * @return 附加对象
     */
    T getAttachedObject();
}
