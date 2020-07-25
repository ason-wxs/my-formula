package cn.wxson.biz;

import java.math.BigDecimal;

/**
 * Title 解析
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@FunctionalInterface
public interface Parser {

    /**
     * 该解析方法，适用场景有如下限制：
     * 1.公式目前仅支持加减乘除法计算
     * 2.公式内分隔符采用"[]"，其他符号不行，且必须成对儿出现
     * 3.每对儿分隔符"[]"里面仅支持两个元素操作，但内部如果还存在分隔符对儿，可看作一个元素
     * 4.计算最小因子，只支持正数和零，暂不支持负数
     * 支持举例：
     * 2 + 3
     * 2 + [ 3 + 4]
     * [ 2 + 3 ] + [ 3 + 4]
     * [ [ 2 + 3 ] - 3 ] * [ 3 / 4]
     * 不支持举例：
     * 2 + 3 + 4    // 多于两个元素
     * 2 + ( 3 + 4 )    // 分隔符必须是中括号
     * 2 + [ 3 + 4 ] + 5    // 多于两个元素
     *
     * @param formula 公式，例如：[ 2 + 3 ] - 1 或 [ 2 + 3 ] - [1 + 4]
     * @return 解析后的计算值
     */
    BigDecimal parse(String formula);
}
