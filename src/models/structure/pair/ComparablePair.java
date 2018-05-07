package models.structure.pair;

/**
 * 可比较大小二元对类
 * <p>
 * 特性：
 * 1、在GenericPair类基础上，提供比较大小功能
 * <p>
 * 注：
 * 1、X类型和Y类型必须继承Comparable接口
 *
 * @param <X> 类型X（必须继承Comparable接口）
 * @param <Y> 类型Y（必须继承Comparable接口）
 */
public class ComparablePair<X extends Comparable<X>, Y extends Comparable<Y>> extends GenericPair<X, Y> implements Comparable<ComparablePair<X, Y>> {
    /**
     * 构造函数
     *
     * @param first  第一个值
     * @param second 第二个值
     */
    public ComparablePair(X first, Y second) {
        /**
         * @modifies:
         *          this.first;
         *          this.second;
         */
        super(first, second);
    }
    
    /**
     * 比较大小
     *
     * @param o 另一个同类型可比较二元对
     * @return 比较结果
     */
    @Override
    public int compareTo(ComparablePair<X, Y> o) {
        /**
         * @effects:
         *          (this.getFirst ().compareTo(o.getFirst ()) != 0) ==> \result = this.getFirst().compareTo(o.getFirst());
         *          (this.getFirst().compareTo(o.getSecond()) == 0) ==> \result = this.getSecond().compareTo(o.getSecond());
         */
        int compare_x = this.getFirst().compareTo(o.getFirst());
        if (compare_x != 0) {
            return compare_x;
        } else {
            return this.getSecond().compareTo(o.getSecond());
        }
    }
}
