package models.application;

/**
 * 基于哈希判定异同的模型类
 */
public class HashBasedModel extends ApplicationModel {
    
    /**
     * 基于hashCode返回值的相同判定
     *
     * @param obj 另一个对象
     * @return 是否相同
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {  // 是自己
            return true;
        }
        if (this.getClass().isInstance(obj)) {  // 同类型
            return this.hashCode() == obj.hashCode();
        } else {
            return false;
        }
    }
}
