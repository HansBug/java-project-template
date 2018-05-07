package models.structure.pair;

/**
 * 无序二元对类
 * <p>
 * 注：
 * 1、无序二元对类的hashCode和equals方法均进行了全部重写
 * 2、故不要将GenericPair和ComparablePair与之进行直接比较（或者说，比较的结果无意义）
 * <p>
 * 建议：
 * 1、可以用来存储无向图中的无向边，使用很方便，且完美支持HashMap等数据结构2333
 *
 * @param <X> 数据类型
 */
public class UnorderedPair<X> extends GenericPair<X, X> {
    /**
     * 构造函数
     *
     * @param first  第一个值
     * @param second 第二个值
     */
    public UnorderedPair(X first, X second) {
        /**
         * @modifies:
         *          \this.first;
         *          \this.second;
         * @effects:
         *          \this.first = first;
         *          \this.second = second;
         */
        super(first, second);
    }
    
    /**
     * 对象相等性判定
     *
     * @param obj 另一个对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        /**
         * @effects:
         *          (\ this = = obj) ==> \result = true;
         *          (obj is an instance of GenericPair) ==> \result = ((\this.getReversed() equals obj) || (\this.getReversed().getReversed() equals obj))
         *          (!(\this == obj) && !(obj is an instance of GenericPair)) ==> \result = false;
         */
        if (this == obj) {
            return true;
        } else if (obj instanceof GenericPair) {
            return this.getReversed().equals(obj) || this.getReversed().getReversed().equals(obj);
        } else {
            return false;
        }
    }
    
    /**
     * 哈希值计算
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        /**
         * @effects:
         *          (\ this.first.hashCode () < \this.second.hashCode()) ==> \result = \super.hashCode();
         *          (\this.first.hashCode() >= \this.second.hashCode()) ==> \result = \super.getReversed().hashCode();
         */
        int first_hash = this.getFirst().hashCode();
        int second_hash = this.getSecond().hashCode();
        if (first_hash < second_hash) {
            return super.hashCode();
        } else {
            return super.getReversed().hashCode();
        }
    }
}
