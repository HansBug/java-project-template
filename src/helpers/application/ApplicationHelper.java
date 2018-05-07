package helpers.application;

import interfaces.application.ApplicationInterface;

import java.util.Random;

/**
 * 全局帮助类
 * <p>
 * 建议：
 * 1、帮助类及其子类均为抽象类
 * 2、一般用于放置一些不适合放在任何其他类中的静态方法
 */
public abstract class ApplicationHelper implements ApplicationInterface {
    /**
     * 随机对象
     */
    private static final Random random = new Random(System.currentTimeMillis());
    
    /**
     * 获取全局随机对象
     *
     * @return 随机对象
     */
    public static Random getRandom() {
        /**
         * @effects:
         *          \result = random;
         */
        return random;
    }
}
