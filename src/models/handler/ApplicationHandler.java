package models.handler;

import exceptions.handler.InvalidInputStreamException;
import exceptions.handler.InvalidPrintStreamException;
import models.application.ApplicationModel;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * 处理器基类
 */
public abstract class ApplicationHandler extends ApplicationModel {
    /**
     * 默认输入流
     */
    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    
    /**
     * 默认输出流
     */
    private static final PrintStream DEFAULT_PRINT_STREAM = System.out;
    
    /**
     * 输入流
     */
    private InputStream inputStream;
    
    /**
     * 输出流
     */
    private PrintStream printStream;
    
    /**
     * 构造函数
     * 使用默认流
     */
    public ApplicationHandler() {
        /**
         * @modifies:
         *          \this.inputStream;
         *          \this.printStream;
         * @effects:
         *          \this.inputStream = DEFAULT_INPUT_STREAM;
         *          \this.printStream = DEFAULT_PRINT_STREAM;
         */
        this.inputStream = DEFAULT_INPUT_STREAM;
        this.printStream = DEFAULT_PRINT_STREAM;
    }
    
    /**
     * 构造函数
     *
     * @param inputStream 输入流
     * @param printStream 打印流
     * @throws InvalidInputStreamException 非法输入流
     * @throws InvalidPrintStreamException 非法输出流
     */
    public ApplicationHandler(InputStream inputStream, PrintStream printStream) throws InvalidInputStreamException, InvalidPrintStreamException {
        /**
         * @modifies:
         *          \this.inputStream;
         *          \this.printStream;
         * @effects:
         *          (inputStream = = null) ==> throw InvalidInputStreamException;
         *          (printStream == null) ==> throw InvalidPrintStreamException;
         *          \this.inputStream = inputStream;
         *          \this.printStream = printStream;
         */
        setInputStream(inputStream);
        setPrintStream(printStream);
    }
    
    /**
     * 获取输入流
     *
     * @return 输入流
     */
    public InputStream getInputStream() {
        /**
         * @effects:
         *          \result == \this.inputStream;
         */
        return inputStream;
    }
    
    /**
     * 获取打印流
     *
     * @return 打印流
     */
    public PrintStream getPrintStream() {
        /**
         * @effects:
         *          \result == \this.printStream;
         */
        return printStream;
    }
    
    /**
     * 设置输入流
     *
     * @param inputStream 输入流
     * @throws InvalidInputStreamException 非法输入流
     */
    public void setInputStream(InputStream inputStream) throws InvalidInputStreamException {
        /**
         * @modifies:
         *          \this.inputStream;
         * @effects:
         *          (inputStream = = null) ==> throw InvalidInputStreamException;
         *          (inputStream != null) ==> (\this.inputStream == inputStream);
         */
        if (inputStream == null) {
            throw new InvalidInputStreamException(inputStream);
        } else {
            this.inputStream = inputStream;
        }
    }
    
    /**
     * 设置打印流
     *
     * @param printStream 打印流
     * @throws InvalidPrintStreamException 非法打印流
     */
    public void setPrintStream(PrintStream printStream) throws InvalidPrintStreamException {
        /**
         * @modifies:
         *          \this.printStream;
         * @effects:
         *          (printStream = = null) ==> throw InvalidPrintStreamException;
         *          (printStream != null) ==> (\this.printStream == printStream);
         */
        if (printStream == null) {
            throw new InvalidPrintStreamException(printStream);
        } else {
            this.printStream = printStream;
        }
    }
    
    /**
     * 处理方法
     *
     * @throws Throwable 任意错误和异常
     */
    public abstract void handle() throws Throwable;
}
