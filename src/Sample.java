import events.thread.ThreadExceptionEvent;
import events.thread.ThreadTriggerEvent;
import events.thread.ThreadTriggerWithReturnValueEvent;
import interfaces.thread.TriggerInterface;
import models.structure.map.HashExpireMap;
import models.file.FileAppendWriter;
import models.file.LogWriter;
import models.thread.circulation.SimpleCirculationThread;
import models.thread.circulation.TimelineTriggerThread;
import models.thread.circulation.TimerThread;
import models.thread.trigger.DelayThread;
import models.thread.trigger.DelayUntilThread;
import models.time.Timestamp;

import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * demo类
 */
public abstract class Sample {
    /**
     * 入口点方法
     *
     * @param args 命令行参数
     * @throws Throwable 异常
     */
    public static void main(String[] args) throws Throwable {
        testTimestamp();
        testWriters();
        testCirculationThread();
        testDelayedThread();
        testHashExpireMap();
        testTimelineTriggerThread();
        testTimerThread();
    }
    
    /**
     * 时间戳类展示
     *
     * @throws Throwable 任意异常
     */
    private static void testTimestamp() throws Throwable {
        sleep(1000);
        System.out.println("");
        
        Timestamp timestamp = new Timestamp();
        System.out.println(String.format("Current timestamp: %s, unix format: %s", timestamp, timestamp.getTimestamp()));
        
        timestamp = timestamp.getOffseted(-60 * 1000);
        System.out.println(String.format("A minute ago: %s, unix format: %s", timestamp, timestamp.getTimestamp()));
    }
    
    /**
     * 文件写入器展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testWriters() throws Throwable {
        sleep(1000);
        System.out.println("");
        
        // file_writer
        FileAppendWriter file_writter = new FileAppendWriter("file.txt", false);
        file_writter.append("23333");
        file_writter.append("2333345");
        file_writter.append("23333456789");
        // 无需关闭
        
        // log_writter
        LogWriter log_writter = new LogWriter("log.txt", false);
        Timestamp timestamp = new Timestamp();
        log_writter.append(String.format("Current time: %s, unix format: %s", timestamp, timestamp.getTimestamp()));
        sleep(1000);
        timestamp = new Timestamp();
        log_writter.append(String.format("Current time: %s, unix format: %s", timestamp, timestamp.getTimestamp()));
    }
    
    /**
     * 循环线程展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testCirculationThread() throws Throwable {
        sleep(1000);
        System.out.println("");
        
        // 循环线程
        SimpleCirculationThread t = new SimpleCirculationThread() {
            private int count;
            
            /**
             * start后，主循环开始前
             * @throws Throwable 任意异常类
             */
            @Override
            public void beforeCirculation() throws Throwable {
                count = 0;
                System.out.println(String.format("[%s] Circulation thread start!", new Timestamp()));
            }
            
            /**
             * 主循环体
             * @throws Throwable 任意异常类
             */
            @Override
            public void circulation() throws Throwable {
                count += 1;
                System.out.println(String.format("[%s] Circulation body triggered! Count is %s.", new Timestamp(), count));
                sleep(200);  // 我不会告诉你改成sleepuntil精度会好很多的2333
            }
            
            /**
             * 主循环退出后
             * @throws Throwable 任意异常类
             */
            @Override
            public void afterCirculation() throws Throwable {
                System.out.println(String.format("[%s] Circulation complete!", new Timestamp()));
                System.out.println(String.format("Final count is %s", count));
            }
            
            /**
             * 捕捉到异常后
             * @param e 异常被触发事件
             */
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
                e.getThrowable().printStackTrace();
            }
        };
        t.start();
        
        // 等待，让循环线程自己跑一会
        sleep(5000);
        
        // 下达停止命令，并阻塞等待
        t.exitGracefully();
        t.join();
    }
    
    /**
     * 延时线程展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testDelayedThread() throws Throwable {
        sleep(1000);
        System.out.println("");
        
        Timestamp timestamp = new Timestamp();
        System.out.println(String.format("Current time: %s", timestamp));
        
        // 延时式线程，start后2s执行
        DelayThread t1 = new DelayThread(2000) {
            @Override
            public void trigger(ThreadTriggerWithReturnValueEvent e) throws Throwable {
                System.out.println(String.format("[%s] delay thread triggered!", new Timestamp()));
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
                e.getThrowable().printStackTrace();
            }
        };
        t1.start();
        
        // 等待式线程，timestamp后5s执行
        // 我不会告诉你出租车3s窗口期用这个弄爽的不行的2333，又准又好写
        DelayUntilThread t2 = new DelayUntilThread(timestamp.getOffseted(5000)) {
            @Override
            public void trigger(ThreadTriggerWithReturnValueEvent e) throws Throwable {
                System.out.println(String.format("[%s] delay until thread triggered!", new Timestamp()));
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
                e.getThrowable().printStackTrace();
            }
        };
        
        t2.start();
        
        t1.join();
        t2.join();
    }
    
    /**
     * HashExpireMap效果展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testHashExpireMap() throws Throwable {
        System.out.println();
        HashExpireMap<Integer, Integer> map = new HashExpireMap<>();
        map.put(23, 1);  // 无限期
        map.put(233, 2, 1000);  // 设定后1000ms超时
        map.put(2333, 3, new Timestamp().getOffseted(823));  // 设定时间点后超时
        
        SimpleCirculationThread t1 = new SimpleCirculationThread() {  // 用于实时观察map内部变化的线程
            @Override
            public void beforeCirculation() throws Throwable {
            
            }
            
            @Override
            public void circulation() throws Throwable {
                System.out.print(String.format("[%s] ", new Timestamp()));
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    System.out.print(String.format("%s: %s, ", entry.getKey(), entry.getValue()));
                }
                System.out.println();
                sleep(1);
            }
            
            @Override
            public void afterCirculation() throws Throwable {
            
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
            
            }
        };
        t1.start();
        
        
        DelayThread t2 = new DelayThread(1500) {  // 1500ms 后结束t1
            @Override
            public void trigger(ThreadTriggerWithReturnValueEvent e) throws Throwable {
                t1.exitGracefully();
            }
            
            @Override
            public void exceptionCaught(ThreadExceptionEvent e) {
                e.getThrowable().printStackTrace();
            }
        };
        t2.start();
        
        t2.join();
        t1.join();
    }
    
    /**
     * 时间线触发器效果展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testTimelineTriggerThread() throws Throwable {
        System.out.println();
        
        TimelineTriggerThread t1 = new TimelineTriggerThread();  // 启动时间线线程
        t1.start();
        
        Timestamp timestamp = new Timestamp();  // 当前时间
        System.out.println(String.format("Current time: %s", timestamp));
        
        t1.add(timestamp.getOffseted(3000), new TriggerInterface() {  // 时间戳后严格3000ms
            @Override
            public void trigger(ThreadTriggerEvent e) {
                System.out.println(String.format("[%s] first trigger", new Timestamp()));
                t1.add(100, new TriggerInterface() {  // 此trigger触发后100ms结束时间线线程
                    @Override
                    public void trigger(ThreadTriggerEvent e) {
                        System.out.println(String.format("[%s] terminate!", new Timestamp()));
                        t1.exitGracefully();
                    }
                });
            }
        });
        
        t1.add(2000, new TriggerInterface() {  // 调用时间后 2000ms
            @Override
            public void trigger(ThreadTriggerEvent e) {
                System.out.println(String.format("[%s] second trigger", new Timestamp()));
            }
        });
        
        t1.join();
    }
    
    /**
     * TimerThread效果展示
     *
     * @throws Throwable 任意异常类
     */
    private static void testTimerThread() throws Throwable {
        System.out.println();
        TimerThread timer1 = new TimerThread(500) {  // 每500ms触发一次
            private int count = 0;
            
            @Override
            public void trigger(ThreadTriggerEvent e) {
                count += 1;
                System.out.println(String.format("[%s] %s", new Timestamp(), count));
                if (count >= 10) this.exitGracefully();  // 你甚至可以在后面再加一个0，你会发现时间误差依然没啥大的变化
            }
        };
        timer1.start();
        timer1.join();
    }
}
