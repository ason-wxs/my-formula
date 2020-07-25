package cn.wxson.biz.impl;

import cn.wxson.biz.Parser;
import cn.wxson.model.Constant;
import cn.wxson.model.Formula;
import cn.wxson.model.Symbol;
import cn.wxson.util.FormulaUtil;
import cn.wxson.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Title 公式解析
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@Slf4j
public class ParserImpl implements Parser {

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
    @Override
    public BigDecimal parse(String formula) {
        // 1.不包含计算符号时，为单数字
        if (FormulaUtil.noSymbol(formula)) {
            String value = StringUtil.clean(formula);
            return new BigDecimal(value);
        }
        // 2.单符号计算时，为直接表达式，例如：2 + 1
        if (FormulaUtil.isSingleSymbol(formula)) {
            String value = StringUtil.clean(formula);
            return directEval(value);
        }
        // 3.多符号计算情况下，前后都是间接表达式，例如：[ 2 + 1 ] + 1    或   [ 2 + 1] + [ 2 + 1 ]
        Formula operator = parseTo(formula);
        BigDecimal first = parse(operator.getFirst());
        BigDecimal second = parse(operator.getSecond());
        return FormulaUtil.eval(first.toString(), second.toString(), operator.getSymbol());
    }

    /**
     * 对多符号计算公式进行解析
     *
     * @param formula 公式，例如：1 + [ 2 + 3]    或    [ 1 + 2 ] + [ 2 + 3]
     * @return 解析结果
     */
    private Formula parseTo(String formula) {
        int startIndex = StringUtils.indexOf(formula, Constant.BRACKETS_START);
        if (startIndex == -1) {
            log.error("[多符号计算情况下，不存在分隔符\"[]\"] [表达式：{}]", formula);
            throw new RuntimeException("[多符号计算情况下，不存在分隔符\"[]\"]");
        }
        int endIndex = StringUtil.index(formula, startIndex);
        String first = StringUtils.substring(formula, startIndex + 1, endIndex);
        String replace = StringUtils.replace(formula, Constant.BRACKETS_START + first + Constant.BRACKETS_END, Constant.SPECIAL);
        int startIndex2 = StringUtils.indexOf(replace, Constant.BRACKETS_START);
        if (startIndex2 == -1) {
            Symbol symbol = FormulaUtil.symbol(replace);
            String second = StringUtils.replaceEach(replace, new String[]{Constant.SPECIAL, symbol.getLiteral()}, new String[]{StringUtils.EMPTY, StringUtils.EMPTY});
            return Formula.builder().first(first).second(second).symbol(symbol).build();
        }
        int endIndex2 = StringUtil.index(replace, startIndex2);
        String second = StringUtils.substring(replace, startIndex2 + 1, endIndex2);
        String replace2 = StringUtils.replace(replace, Constant.BRACKETS_START + second + Constant.BRACKETS_END, Constant.SPECIAL);
        Symbol symbol = FormulaUtil.symbol(replace2);
        return Formula.builder().first(first).second(second).symbol(symbol).build();
    }

    /**
     * 对单符号公式进行直接计算
     *
     * @param formula 公式
     * @return 计算结果
     */
    public static BigDecimal directEval(String formula) {
        Symbol symbol = FormulaUtil.symbol(formula);
        int index = StringUtils.indexOf(formula, symbol.getLiteral());
        String first = StringUtils.substring(formula, 0, index);
        String second = StringUtils.substring(formula, index + 1);
        return FormulaUtil.eval(first, second, symbol);
    }
}
