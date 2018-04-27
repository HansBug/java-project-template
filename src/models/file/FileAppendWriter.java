package models.file;

import configs.application.ApplicationConfig;
import models.application.ApplicationModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 文件追加写入器
 * <p>
 * 功能：
 * 1、文件追加写信息
 * <p>
 * 特性：
 * 1、在构造类时可以选择是否为追加模式（即是在原来基础上继续写还是清空）
 * 2、支持暂存写入行信息（出现IO错误时待写入信息不会丢失，将在下一次一并写入）
 * <p>
 * 注意：
 * 1、该类为线程安全类
 * 2、但是，在文件层面未进行线程安全处理，故依然需要在涉及多线程访问的时候注意进行基于文件目标的线程安全封装
 */
public class FileAppendWriter extends ApplicationModel {
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 保存的行数据
     */
    private ArrayList<String> saved_lines = new ArrayList<>();
    
    /**
     * 初始化构造函数（默认为非追加模式）
     *
     * @param filename 写入目标
     */
    public FileAppendWriter(String filename) {
        this(filename, false);
    }
    
    /**
     * 初始化构造函数
     *
     * @param filename 写入目标
     * @param append   是否为追加模式（否则清空文件原内容）
     */
    public FileAppendWriter(String filename, boolean append) {
        this.filename = filename;
        if (!append) this.clear();
    }
    
    
    /**
     * 获取文件名
     *
     * @return 文件名
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * 清空暂存区和目标文件
     */
    public synchronized void clear() {
        this.saved_lines.clear();
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(this.filename));
            writer.close();
        } catch (IOException e) {
            try {
                if (writer != null) writer.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 添加新行
     *
     * @param line 新行
     */
    private synchronized void addToQueue(String line) {
        this.saved_lines.add(line);
    }
    
    /**
     * 导出队列信息
     */
    private synchronized void dumpQueue() {
        FileWriter fw = null;
        ArrayList<String> new_queue = new ArrayList<>(this.saved_lines);
        try {
            fw = new FileWriter(new File(this.filename), true);
            Iterator<String> iterator = new_queue.iterator();
            while (iterator.hasNext()) {
                fw.write(iterator.next() + ApplicationConfig.BREAK_LINE);
                iterator.remove();
            }
            fw.close();
            this.saved_lines.clear();
        } catch (IOException e) {
            try {
                if (fw != null) fw.close();
                this.saved_lines = new_queue;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    /**
     * 追加行信息（且自动输出）
     *
     * @param line 行信息
     */
    public synchronized void append(String line) {
        this.addToQueue(line);
        this.dumpQueue();
    }
}
