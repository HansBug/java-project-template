package models.data.property;

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
        this(lower_bound, true, upper_bound, true, data);
    }
    
    /**
     * 构造函数（默认包含上下界，初始值null）
     *
     * @param lower_bound 下界
     * @param upper_bound 上界
     */
    public RangePropertyModel(T lower_bound, T upper_bound) {
        this(lower_bound, upper_bound, null);
    }
    
    /**
     * 下界验证（下界为null视为无下界）
     *
     * @param data 原数据
     * @return 验证结果
     */
    private boolean validateLowerBound(T data) {
        if (this.lower_bound == null) return true;
        int compare = this.lower_bound.compareTo(data);
        return (compare < 0) || ((compare == 0) && (this.include_lower_bound));
    }
    
    /**
     * 上界验证（上界为null视为无上界）
     *
     * @param data 原数据
     * @return 验证结果
     */
    private boolean validateUpperBound(T data) {
        if (this.upper_bound == null) return true;
        int compare = this.upper_bound.compareTo(data);
        return (compare > 0) || ((compare == 0) && (this.include_upper_bound));
    }
    
    /**
     * 数据验证
     *
     * @param value 原数据
     * @return 验证结果
     */
    @Override
    public boolean validate(T value) {
        if (!super.validate(value)) return false;
        return this.validateLowerBound(value) && this.validateUpperBound(value);
    }
    
    /**
     * 生成错误信息
     *
     * @param data 错误原数据
     * @return 错误信息
     */
    @Override
    protected String getErrorMessage(T data) {
        if (data == null) {
            return "Property value is null";
        } else {
            if (!this.validateLowerBound(data)) {
                if (this.include_lower_bound) {
                    return String.format("Property value \"%s\" should be no less than \"%s\".", data, this.lower_bound);
                } else {
                    return String.format("Property value \"%s\" should be more than \"%s\".", data, this.lower_bound);
                }
            } else if (!this.validateUpperBound(data)) {
                if (this.include_upper_bound) {
                    return String.format("Property value \"%s\" should be no more than \"%s\".", data, this.upper_bound);
                } else {
                    return String.format("Property value \"%s\" should be less than \"%s\".", data, this.upper_bound);
                }
            } else {
                return String.format("Property value \"%s\" is not in range from %s to %s.", data, this.lower_bound, this.upper_bound);
            }
        }
    }
}
