package models.thread.circulation;

/**
 * 简单轮询线程
 * <p>
 * 特性：
 * 1、不支持自定义阻塞（一律设置为不阻塞）
 * 2、支持从外部下达退出指令
 */
public abstract class SimpleCirculationThread extends NotifyCirculationThread {
    /**
     * 是否处于等待退出模式
     */
    private boolean waiting_for_quit = false;
    
    /**
     * 一律不阻塞
     *
     * @return true
     */
    @Override
    public boolean permitCirculation() {
        /**
         * @effects:
         *          \result = true;
         */
        return true;
    }
    
    /**
     * 检测到等待退出模式时禁止继续循环
     *
     * @return 是否允许继续循环
     */
    @Override
    public boolean permitContinue() {
        /**
         * @effects:
         *          \result = !\this.waiting_for_quit;
         */
        return !waiting_for_quit;
    }
    
    /**
     * 发出退出指令
     */
    public void exitGracefully() {
        /**
         * @modifies:
         *          \this.waiting_for_quit;
         * @effects:
         *          \this.waiting_for_quit = true;
         */
        this.waiting_for_quit = true;
    }
}
