package interfaces.data.validator;

import exceptions.data.data.InvalidDataException;
import interfaces.application.ApplicationInterface;

/**
 * 模型数据验证接口
 * <p>
 * 用途：
 * 1、用来验证数据是否合法（比如作业中的各种格式错误和无效请求）
 */
public interface ModelValidator extends ApplicationInterface {
    /**
     * 数据验证
     *
     * @throws InvalidDataException 属性数据异常
     */
    void validate() throws InvalidDataException;
    
    
}
