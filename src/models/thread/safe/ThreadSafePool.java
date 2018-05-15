package models.thread.safe;

import interfaces.data.translator.Translator;
import models.thread.ApplicationThreadModel;

import java.util.HashMap;

/**
 * 线程安全对象池
 * <p>
 * 特性：
 * 1、从同一个安全对象池内通过同一个索引值获取到的对象可以确保永远为同一对象
 * <p>
 * 用途：
 * 1、基于以上的特性，可以用于快速使用同步锁
 * 2、可定制翻译函数，更加灵活（对于文件系统中含有相对路径的情况尤其好用）
 *
 * @param <T> 索引值类型
 */
public class ThreadSafePool<T> extends ApplicationThreadModel {
    /**
     * 线程安全包裹对象
     */
    private class ThreadSafeObject {
        /**
         * 包裹对象
         */
        private final T object;
        
        /**
         * 构造函数
         *
         * @param object 包裹对象
         */
        private ThreadSafeObject(T object) {
            /**
             * @modifies:
             *          \this.object;
             * @effects:
             *          \this.object = object;
             */
            this.object = object;
        }
        
        /**
         * 获取对象
         *
         * @return 包裹对象
         */
        public T getObject() {
            /**
             * @effects:
             *          \result = \this.object;
             */
            return object;
        }
    }
    
    /**
     * 内置转译函数
     */
    private final Translator<T, T> translator;
    
    /**
     * 对象存储
     */
    private final HashMap<T, ThreadSafeObject> map;
    
    /**
     * 构造函数
     *
     * @param translator 翻译函数
     */
    public ThreadSafePool(Translator<T, T> translator) {
        /**
         * @modifies:
         *          \this.translator;
         *          \this.map;
         * @effects:
         *          \this.translator = translator;
         *          \this.map = new HashMap();
         */
        this.translator = translator;
        this.map = new HashMap<>();
    }
    
    /**
     * 构造函数（使用直译函数，即不翻译）
     */
    public ThreadSafePool() {
        /**
         * @modifies:
         *          \this.translator;
         *          \this.map;
         * @effects:
         *          \this.translator = default translator;
         *          \this.map = new HashMap();
         */
        this(new Translator<T, T>() {
            @Override
            public T translate(T origin) {
                return origin;
            }
        });
    }
    
    /**
     * 获取锁定对象
     *
     * @param value 索引值
     * @return 锁定对象
     */
    public ThreadSafeObject getSafeObject(T value) {
        /**
         * @modifies:
         *          \this.map;
         * @effects:
         *          \result = \this.map.get(actual_value);
         * @notice:
         *          actual_value is defined as the value after translated by the translator;
         */
        synchronized (this.map) {
            T actual_value = this.translator.translate(value);
            if (!this.map.containsKey(actual_value)) {
                this.map.put(actual_value, new ThreadSafeObject(actual_value));
            }
            return this.map.get(actual_value);
        }
    }
}
