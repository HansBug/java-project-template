package models.application;

/**
 * 基于哈希判定异同的模型类
 * <p>
 * 特性：
 * 1、equals函数会进行相同性判定
 * <p>
 * 注意：
 * 1、equals是基于数据类型和哈希值的判定，可能会存在撞码的情况，导致HashMap等数据结构产生误判
 * 2、感兴趣的话可以自行读一读java hashCode的源码
 * <p>
 * 建议：
 * 1、在容易撞码的情况下不要使用此类
 * 2、或者在子类中进行判定重写
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
        /**
         * @effects:
         *          (\ this = = obj) ==> \result = true;
         *          (\this.getClass().isInstance(obj)) ==> \result = (\this.hashCode() == obj.hashCode());
         *          ((\this != obj) && (!\this.getClass().isInstance(obj))) ==> \result = false;
         */
        if (this == obj) {  // 是自己
            return true;
        } else if (this.getClass().isInstance(obj)) {  // 同类型，进行基于哈希的判定
            return this.hashCode() == obj.hashCode();
        } else {
            return false;
        }
    }
}
