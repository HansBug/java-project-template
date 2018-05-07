package models.structure.map;

import interfaces.application.ApplicationInterface;
import models.structure.object.TimeBasedObject;
import models.time.Timestamp;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 支持超时功能的HashMap
 * <p>
 * 特性：
 * 1、支持超时功能，即一个键值对只有一定的有效期（也可以设置为无限有效期），过期后自动删除
 * 2、采用局部+整体增量维护算法，理论上性能可靠且资源占用低
 * 3、对核心操作均进行了线程安全保护
 * <p>
 * 注：
 * 1、该类目前尚未经过大量生产环境测试，如果遇到bug请联系作者
 * 2、该类大部分方法规格与父类HashMap一致（之所以重写只是因为需要进行数据同步性维护），故大部分方法将省略规格
 *
 * @param <K> key类型
 * @param <V> value类型
 */
public class HashExpireMap<K, V> extends HashMap<K, V> implements ApplicationInterface {
    /**
     * 带时间的key对象
     */
    private class TimeBasedKey extends TimeBasedObject<K> {
        /**
         * 构造函数
         *
         * @param key         key值
         * @param expire_time 超时时间(null为无穷)
         */
        public TimeBasedKey(K key, Timestamp expire_time) {
            super(key);
            setTimestamp(expire_time);
        }
        
        /**
         * 获取key值
         *
         * @return key值
         */
        public K getKey() {
            return super.getObject();
        }
    }
    
    /**
     * 超时对象队列
     */
    private final PriorityQueue<TimeBasedKey> key_queue;
    
    /**
     * key -> 超时对象映射
     */
    private final HashMap<K, TimeBasedKey> key_map;
    
    /**
     * 构造函数
     */
    public HashExpireMap() {
        this.key_queue = new PriorityQueue<>(new Comparator<TimeBasedKey>() {
            @Override
            public int compare(TimeBasedKey o1, TimeBasedKey o2) {
                Timestamp timestamp1 = o1.getTimestamp();
                Timestamp timestamp2 = o2.getTimestamp();
                if ((timestamp1 == null) && (timestamp2 == null)) {
                    return 0;
                } else if (timestamp1 == null) {
                    return 1;
                } else if (timestamp2 == null) {
                    return -1;
                } else {
                    return Timestamp.compare(timestamp1, timestamp2);
                }
            }
        });
        this.key_map = new HashMap<>();
    }
    
    /**
     * 清空所有超时的key
     */
    protected synchronized void removeExpiredKeys() {
        Timestamp present = new Timestamp();
        while (!this.key_queue.isEmpty()) {
            TimeBasedKey key_object = this.key_queue.peek();
            if (key_object == null) continue;
            if (!checkExpired(key_object.getKey())) break;
        }
    }
    
    /**
     * 尝试删除某指定的超时key
     *
     * @param key key
     * @return 是否删除
     */
    protected synchronized boolean checkExpired(K key) {
        return checkExpired(key, new Timestamp());
    }
    
    /**
     * 尝试删除某指定的超时key（以某时间为节点）
     *
     * @param key     key
     * @param present 时间节点
     * @return 是否删除
     */
    protected synchronized boolean checkExpired(K key, Timestamp present) {
        TimeBasedKey key_object = this.key_map.get(key);
        Timestamp timestamp = key_object.getTimestamp();
        if ((timestamp == null) || (Timestamp.compare(timestamp, present) > 0)) {  // not expired
            return false;
        } else {  // has expired
            this.remove(key_object.getKey());
            return true;
        }
    }
    
    /**
     * 删除指定的key
     *
     * @param key key
     * @return key原本的value
     */
    @Override
    public synchronized V remove(Object key) {
        TimeBasedKey key_object = this.key_map.remove(key);
        this.key_queue.remove(key_object);
        return super.remove(key);
    }
    
    /**
     * 清空数据结构
     */
    @Override
    public synchronized void clear() {
        super.clear();
        this.key_map.clear();
        this.key_queue.clear();
    }
    
    /**
     * 检测是否为空
     *
     * @return 是否为空
     */
    @Override
    public synchronized boolean isEmpty() {
        this.removeExpiredKeys();
        return super.isEmpty();
    }
    
    
    /**
     * 获取某键对应值
     *
     * @param key key
     * @return 对应值
     */
    @Override
    public synchronized V get(Object key) {
        this.checkExpired((K) key);
        return super.get(key);
    }
    
    /**
     * 获取值或者返回默认值
     *
     * @param key          key
     * @param defaultValue key未检测到时返回的默认值
     * @return 返回值
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        this.checkExpired((K) key);
        return super.getOrDefault(key, defaultValue);
    }
    
    /**
     * 将key键值设定为value，expire_after毫秒后超时
     *
     * @param key          key
     * @param value        value
     * @param expire_after 超市期限
     * @return 设置的值
     */
    public synchronized V put(K key, V value, long expire_after) {
        return this.put(key, value, new Timestamp().getOffseted(expire_after));
    }
    
    /**
     * 将key键值设定为value，expire_time时超时
     *
     * @param key         key
     * @param value       value
     * @param expire_time 超时时间(null表示无限期）
     * @return 设置的值
     */
    public synchronized V put(K key, V value, Timestamp expire_time) {
        this.remove(key);
        TimeBasedKey key_object = new TimeBasedKey(key, expire_time);
        this.key_queue.add(key_object);
        this.key_map.put(key, key_object);
        return super.put(key, value);
    }
    
    /**
     * 将key键值设定为value（无限期）
     *
     * @param key   key
     * @param value value
     * @return 设置的值
     */
    @Override
    public synchronized V put(K key, V value) {
        return this.put(key, value, null);
    }
    
    /**
     * 空缺时赋值
     *
     * @param key   key
     * @param value value
     * @return 设置的值
     */
    @Override
    public V putIfAbsent(K key, V value) {
        this.checkExpired(key);
        return super.putIfAbsent(key, value);
    }
    
    /**
     * 返回键集合
     *
     * @return 键集合
     */
    @Override
    public synchronized Set<K> keySet() {
        this.removeExpiredKeys();
        return super.keySet();
    }
    
    /**
     * 返回entry集合
     *
     * @return entry集合
     */
    @Override
    public synchronized Set<Entry<K, V>> entrySet() {
        this.removeExpiredKeys();
        return super.entrySet();
    }
    
    /**
     * 获取哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        this.removeExpiredKeys();
        return super.hashCode();
    }
    
    /**
     * 判断是否包含key
     *
     * @param key key
     * @return 是否包含
     */
    @Override
    public boolean containsKey(Object key) {
        this.checkExpired((K) key);
        return super.containsKey(key);
    }
    
    /**
     * 判断是否包含value
     *
     * @param value value
     * @return 是否包含
     */
    @Override
    public boolean containsValue(Object value) {
        this.removeExpiredKeys();
        return super.containsValue(value);
    }
    
    /**
     * 获取值集合
     *
     * @return 值集合
     */
    @Override
    public Collection<V> values() {
        this.removeExpiredKeys();
        return super.values();
    }
    
    /**
     * 获取大小
     *
     * @return 大小
     */
    @Override
    public int size() {
        this.removeExpiredKeys();
        return super.size();
    }
    
    /**
     * 全部设置
     *
     * @param m m
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.removeExpiredKeys();
        super.putAll(m);
    }
    
    /**
     * 删除键值对
     *
     * @param key   key
     * @param value value
     * @return 是否删除
     */
    @Override
    public boolean remove(Object key, Object value) {
        this.checkExpired((K) key);
        return super.remove(key, value);
    }
    
    /**
     * 替换键值对
     *
     * @param key      key
     * @param oldValue 旧value
     * @param newValue 新value
     * @return 是否替换成功
     */
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        this.checkExpired(key);
        return super.replace(key, oldValue, newValue);
    }
    
    /**
     * 计算函数
     *
     * @param key               key
     * @param remappingFunction 映射函数接口
     * @return 计算值
     */
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        this.checkExpired(key);
        return super.compute(key, remappingFunction);
    }
    
    /**
     * 计算函数（缺少时计算）
     *
     * @param key             key
     * @param mappingFunction 函数映射接口
     * @return 计算值
     */
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        this.checkExpired(key);
        return super.computeIfAbsent(key, mappingFunction);
    }
    
    /**
     * 如果缺少则计算
     *
     * @param key               key
     * @param remappingFunction 重映射函数接口
     * @return 计算值
     */
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        this.checkExpired(key);
        return super.computeIfPresent(key, remappingFunction);
    }
    
    /**
     * 合并
     *
     * @param key               key
     * @param value             value
     * @param remappingFunction 重新映射函数接口
     * @return 合并值
     */
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        this.checkExpired(key);
        return super.merge(key, value, remappingFunction);
    }
    
    /**
     * 值替换
     *
     * @param key   key
     * @param value value
     * @return 替换值
     */
    @Override
    public V replace(K key, V value) {
        this.checkExpired(key);
        return super.replace(key, value);
    }
    
    /**
     * 枚举
     *
     * @param action 枚举函数接口
     */
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.removeExpiredKeys();
        super.forEach(action);
    }
    
    /**
     * 全部替换
     *
     * @param function 替换函数接口
     */
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.removeExpiredKeys();
        super.replaceAll(function);
    }
    
    /**
     * finalize
     *
     * @throws Throwable 任意异常类
     */
    @Override
    protected void finalize() throws Throwable {
        this.removeExpiredKeys();
        super.finalize();
    }
    
    /**
     * 转为字符串
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        this.removeExpiredKeys();
        return super.toString();
    }
}
