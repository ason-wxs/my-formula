package cn.wxson.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Title 计算符号
 * 目前，仅支持：加减乘除
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@AllArgsConstructor
public enum Symbol {
    ADD("+", 2, "加"),
    SUBTRACT("-", 2, "减"),
    MULTIPLY("*", 1, "乘"),
    DIVIDE("/", 1, "除");
    /**
     * 计算符字符串
     */
    @Setter
    @Getter
    private String literal;
    /**
     * 计算符优先级
     */
    @Setter
    @Getter
    private int priority;
    /**
     * 计算符名称
     */
    private String name;
}