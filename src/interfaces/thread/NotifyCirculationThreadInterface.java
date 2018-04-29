package interfaces.thread;

/**
 * 提醒轮询线程接口
 */
public interface NotifyCirculationThreadInterface extends ApplicationThreadInterface {
    /**
     * 轮询开始前执行
     *
     * @throws Throwable 异常类
     */
    public void beforeCirculation() throws Throwable;
    
    /**
     * 是否允许继续轮询（false则跳出循环）
     *
     * @return 是否允许继续
     * @throws Throwable 异常类
     */
    public boolean permitContinue() throws Throwable;
    
    /**
     * 是否允许立刻执行下一次轮询（false则进行一次对象阻塞）
     *
     * @return 是否允许立即执行
     * @throws Throwable 异常类
     */
    public boolean permitCirculation() throws Throwable;
    
    /**
     * 轮询体程序
     *
     * @throws Throwable 异常类
     */
    public void circulation() throws Throwable;
    
    /**
     * 轮询结束后执行
     *
     * @throws Throwable 异常类
     */
    public void afterCirculation() throws Throwable;
}
