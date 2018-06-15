package exceptions.handler;

import java.io.PrintStream;

/**
 * 非法打印流异常
 */
public class InvalidPrintStreamException extends HandlerException{
    /**
     * 打印流
     */
    private final PrintStream printStream;
    
    /**
     * 构造函数
     * @param printStream 打印流
     */
    public InvalidPrintStreamException(PrintStream printStream) {
        /**
         * @modifies:
         *          \this.message;
         *          \this.printStream;
         * @effects:
         *          \this.message == "Invalid print stream.";
         *          \this.printStream = printStream;
         */
        super("Invalid print stream.");
        this.printStream = printStream;
    }
    
    /**
     * 获取打印流
     * @return 打印流
     */
    public PrintStream getPrintStream() {
        /**
         * @effects:
         *          \result == \this.printStream;
         */
        return printStream;
    }
}
