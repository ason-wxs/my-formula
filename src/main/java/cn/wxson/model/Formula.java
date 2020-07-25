package cn.wxson.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Title 公式
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@Setter
@Getter
@Builder
public class Formula {
    /**
     * 第一位表达式
     */
    private String first;
    /**
     * 第二位表达式
     */
    private String second;
    /**
     * 表达式间的计算符
     */
    private Symbol symbol;
}