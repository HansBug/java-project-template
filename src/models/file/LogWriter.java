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
        /**
         * @modifies:
         *          \this.filename;
         * @effects:
         *          \this.filename = filename;
         *          (!append) ==> \this.clear();
         */
        super(filename, append);
    }
    
    /**
     * 构造函数
     *
     * @param filename 文件名
     */
    public LogWriter(String filename) {
        /**
         * @modifies:
         *          \this.filename;
         * @effects:
         *          \this.filename = filename;
         */
        super(filename);
    }
    
    /**
     * 追加信息
     *
     * @param line 行信息
     */
    public synchronized void append(String line) {
        /**
         * @modifies:
         *          \this.saved_lines;
         *          this output file defined;
         * @effects:
         *          String line with the timestamp will be added into the tail of \this.saved_lines;
         *          the lines in \this.saved_lines appended into the output file defined;
         *          the lines dumped successfully will be remove from \this.saved_lines;
         */
        super.append(String.format("[%s] %s", (new Timestamp()), line));
    }
    
}
