package configs.application;

import interfaces.application.ApplicationInterface;

/**
 * 全局设置类
 * <p>
 * 用途：
 * 1、写入一些全局设置（相关参数一般使用public static final进行定义）
 * <p>
 * 建议：
 * 1、该类仅用于放置全局设置参数
 * 2、和某个类密切相关的常数建议写在特定的类中
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
