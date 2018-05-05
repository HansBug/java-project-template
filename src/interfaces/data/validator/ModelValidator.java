package interfaces.data.validator;

import exceptions.data.property.InvalidPropertyException;
import interfaces.application.ApplicationInterface;

import java.util.List;

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
     * @throws InvalidPropertyException 属性数据异常
     */
    void validate() throws InvalidPropertyException;
    
    /**
     * 安全型验证（无异常抛出，只返回true/false）
     *
     * @return 验证结果
     */
    default boolean isValid() {
        try {
            this.validate();
            return true;
        } catch (InvalidPropertyException e) {
            return false;
        }
    }
}
