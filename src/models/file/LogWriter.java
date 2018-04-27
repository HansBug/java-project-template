package models.file;

import models.time.Timestamp;

/**
 * 日志写入器
 * <p>
 * 特性：
 * 1、继承FileAppendWriter
 * 2、在每一行前增加时间戳信息
 * 3、线程安全性等信息同FileAppendWriter
 */
public class LogWriter extends FileAppendWriter {
    /**
     * 构造函数
     *
     * @param filename 文件名
     * @param append   是否为追加模式
     */
    public LogWriter(String filename, boolean append) {
        super(filename, append);
    }
    
    /**
     * 构造函数
     *
     * @param filename 文件名
     */
    public LogWriter(String filename) {
        super(filename);
    }
    
    /**
     * 追加信息
     *
     * @param line 行信息
     */
    public synchronized void append(String line) {
        super.append(String.format("[%s] %s", (new Timestamp()), line));
    }
    
}
