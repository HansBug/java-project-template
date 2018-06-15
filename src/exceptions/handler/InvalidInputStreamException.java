package exceptions.handler;

import java.io.InputStream;

public class InvalidInputStreamException extends HandlerException {
    /**
     * 输入流
     */
    private final InputStream inputStream;
    
    /**
     * 构造函数
     *
     * @param inputStream 输入流
     */
    public InvalidInputStreamException(InputStream inputStream) {
        /**
         * @modifies:
         *          \this.message;
         *          \this.inputStream;
         * @effects:
         *          \this.message == "Invalid input stream";
         *          \this.inputStream == inputStream;
         */
        super("Invalid input stream.");
        this.inputStream = inputStream;
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
}
