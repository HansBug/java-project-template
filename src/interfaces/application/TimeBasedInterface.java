package interfaces.application;

import models.time.Timestamp;

/**
 * 基于时间戳的接口
 * <p>
 * 需求：
 * 1、对外提供时间信息查询支持
 * <p>
 * 用途：
 * 1、可用于获取时间信息
 */
public interface TimeBasedInterface extends ApplicationInterface {
    /**
     * 获取时间戳信息
     *
     * @return 时间戳
     */
    Timestamp getTimestamp();
}
