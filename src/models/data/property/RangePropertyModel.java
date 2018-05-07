package models.data.property;

import exceptions.data.property.InvalidPropertyException;
import exceptions.data.property.OutOfRangePropertyException;

/**
 * 范围判定验证器
 * <p>
 * 用途：
 * 1、可设定上下界（null表示不限制）
 * 2、可设定是否必须被真包含
 * <p>
 * 注：
 * 1、数据类型需要支持Comparable接口
 *
 * @param <T> 数据类型（需要支持Comparable接口）
 */
public class RangePropertyModel<T extends Comparable<T>> extends NotNullPropertyModel<T> {
    /**
     * 下界
     */
    private T lower_bound;
    /**
     * 是否包含下界
     */
    private boolean include_lower_bound;
    /**
     * 上界
     */
    private T upper_bound;
    /**
     * 是否包含上界
     */
    private boolean include_upper_bound;
    
    /**
     * 构造函数
     *
     * @param lower_bound         下界
     * @param include_lower_bound 是否包含下界
     * @param upper_bound         上界
     * @param include_upper_bound 是否包含上界
     * @param data                初始值
     */
    public RangePropertyModel(T lower_bound, boolean include_lower_bound, T upper_bound, boolean include_upper_bound, T data) {
        /**
         * @modifies:
         *          \this.lower_bound;
         *          \this.include_lower_bound;
         *          \this.upper_bound;
         *          \this.include_upper_bound;
         *          \this.data;
         * @effects:
         *          \this.lower_bound = lower_bound;
         *          \this.include_lower_bound = include_lower_bound;
         *          \this.upper_bound = upper_bound;
         *          \this.include_upper_bound = include_upper_bound;
         *          \this.data = data;
         */
        this.lower_bound = lower_bound;
        this.include_lower_bound = include_lower_bound;
        this.upper_bound = upper_bound;
        this.include_upper_bound = include_upper_bound;
        this.data = data;
    }
    
    /**
     * 构造函数（初始值null）
     *
     * @param lower_bound         下界
     * @param include_lower_bound 是否包含下界
     * @param upper_bound         上界
     * @param include_upper_bound 是否包含上界
     */
    public RangePropertyModel(T lower_bound, boolean include_lower_bound, T upper_bound, boolean include_upper_bound) {
        /**
         * @modifies:
         *          \this.lower_bound;
         *          \this.include_lower_bound;
         *          \this.upper_bound;
         *          \this.include_upper_bound;
         *          \this.data;
         * @effects:
         *          \this.lower_bound = lower_bound;
         *          \this.include_lower_bound = include_lower_bound;
         *          \this.upper_bound = upper_bound;
         *          \this.include_upper_bound = include_upper_bound;
         *          \this.data = null;
         */
        this(lower_bound, include_lower_bound, upper_bound, include_upper_bound, null);
    }
    
    /**
     * 构造函数（默认包含上下界）
     *
     * @param lower_bound 下界
     * @param upper_bound 上界
     * @param data        初始值
     */
    public RangePropertyModel(T lower_bound, T upper_bound, T data) {
        /**
         * @modifies:
         *          \this.lower_bound;
         *          \this.include_lower_bound;
         *          \this.upper_bound;
         *          \this.include_upper_bound;
         *          \this.data;
         * @effects:
         *          \this.lower_bound = lower_bound;
         *          \this.include_lower_bound = true;
         *          \this.upper_bound = upper_bound;
         *          \this.include_upper_bound = true;
         *          \this.data = data;
         */
        this(lower_bound, true, upper_bound, true, data);
    }
    
    /**
     * 构造函数（默认包含上下界，初始值null）
     *
     * @param lower_bound 下界
     * @param upper_bound 上界
     */
    public RangePropertyModel(T lower_bound, T upper_bound) {
        /**
         * @modifies:
         *          \this.lower_bound;
         *          \this.include_lower_bound;
         *          \this.upper_bound;
         *          \this.include_upper_bound;
         *          \this.data;
         * @effects:
         *          \this.lower_bound = lower_bound;
         *          \this.include_lower_bound = true;
         *          \this.upper_bound = upper_bound;
         *          \this.include_upper_bound = true;
         *          \this.data = null;
         */
        this(lower_bound, upper_bound, null);
    }
    
    /**
     * 下界验证（下界为null视为无下界）
     *
     * @param value 原数据
     * @return 验证结果
     */
    private boolean validateLowerBound(T value) {
        /**
         * @effects:
         *          (\ this.lower_bound = = null) ==> \result = true;
         *          \result = ((\this.lower_bound != null) && ((\this.lower_bound < value) || ((\this.lower_bound == value) && (\this.include_lower_bound))));
         * @notice:
         *          < and > and == operation is realized by Comparable interface.
         */
        if (this.lower_bound == null) return true;
        int compare = this.lower_bound.compareTo(value);
        return (compare < 0) || ((compare == 0) && (this.include_lower_bound));
    }
    
    /**
     * 上界验证（上界为null视为无上界）
     *
     * @param value 原数据
     * @return 验证结果
     */
    private boolean validateUpperBound(T value) {
        /**
         * @effects:
         *          (\ this.upper_bound = = null) ==> \result = true;
         *          \result = ((\this.upper_bound != null) && ((\this.upper_bound > value) || ((\this.upper_bound == value) && (\this.include_upper_bound))));
         * @notice:
         *          < and > and == operation is realized by Comparable interface.
         */
        if (this.upper_bound == null) return true;
        int compare = this.upper_bound.compareTo(value);
        return (compare > 0) || ((compare == 0) && (this.include_upper_bound));
    }
    
    /**
     * 数据验证
     *
     * @param value 原数据
     * @throws InvalidPropertyException 非法数据异常
     */
    @Override
    public void validate(T value) throws InvalidPropertyException {
        /**
         * @effects:
         *          normal_behaviour:
         *          validated by super.validate(T data)
         *          value checked by the range define by private properties whether in the proper range
         *
         *          exceptional behaviour(OutOfRangePropertyException): not in proper range
         */
        super.validate(value);
        if (!this.validateLowerBound(value)) {
            if (this.include_lower_bound) {
                throw new OutOfRangePropertyException(value, String.format("Property value \"%s\" should be no less than \"%s\".", value, this.lower_bound));
            } else {
                throw new OutOfRangePropertyException(value, String.format("Property value \"%s\" should be more than \"%s\".", value, this.lower_bound));
            }
        } else if (!this.validateUpperBound(value)) {
            if (this.include_upper_bound) {
                throw new OutOfRangePropertyException(value, String.format("Property value \"%s\" should be no more than \"%s\".", value, this.upper_bound));
            } else {
                throw new OutOfRangePropertyException(value, String.format("Property value \"%s\" should be less than \"%s\".", value, this.upper_bound));
            }
        }
    }
}
