package cn.wxson.biz;

import java.math.BigDecimal;

/**
 * Title 计算
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@FunctionalInterface
public interface Eval {

    /**
     * 计算方法
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @return 计算结果
     */
    BigDecimal eval(String first, String second);
}
