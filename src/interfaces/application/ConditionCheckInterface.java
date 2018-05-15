package interfaces.application;

/**
 * 情况检查接口
 */
public interface ConditionCheckInterface extends ApplicationInterface {
    /**
     * 检查状况
     * @return 状况
     */
    boolean checkCondition();
}
