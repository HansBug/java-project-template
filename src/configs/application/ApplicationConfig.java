package configs.application;

import interfaces.application.ApplicationInterface;

/**
 * 全局设置类
 * 用于写入一些全局设置（相关参数一般使用public static final进行定义）
 */
public abstract class ApplicationConfig implements ApplicationInterface {
    /**
     * 示例全局设置
     */
    public static final int SAMPLE_GLOBAL_CONFIG = 1;
    
    /**
     * 系统换行符
     */
    public static final String BREAK_LINE = System.getProperty("line.separator");
}
