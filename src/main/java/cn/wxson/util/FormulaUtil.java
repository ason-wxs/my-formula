package cn.wxson.util;

import cn.wxson.biz.Eval;
import cn.wxson.factory.EvalFactory;
import cn.wxson.model.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/**
 * Title 计算辅助工具
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@Slf4j
public final class FormulaUtil {

    /**
     * 公式计算
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @param symbol 计算符
     * @return 计算结果
     */
    public static BigDecimal eval(String first, String second, Symbol symbol) {
        Eval eval = EvalFactory.create(symbol);
        return eval.eval(first, second);
    }

    /**
     * 根据计算符字符串，获取计算符对象
     *
     * @param formula 公式
     * @return 计算符对象
     */
    public static Symbol symbol(String formula) {
        Optional<Symbol> first = Arrays.stream(Symbol.values()).filter(symbol -> StringUtils.contains(formula, symbol.getLiteral())).findFirst();
        if (!first.isPresent()) {
            log.error("[公式内不包含任何计算符] [公式：{}]", formula);
            throw new NullPointerException("[公式内不包含任何计算符]");
        }
        return first.get();
    }

    /**
     * 根据计算公式，判断是否只有一个计算符
     *
     * @param formula 公式
     * @return 判断结果
     */
    public static boolean isSingleSymbol(String formula) {
        return countSymbol(formula) == 1;
    }

    /**
     * 根据计算公式，获取计算符个数
     *
     * @param formula 公式
     * @return 个数
     */
    public static int countSymbol(String formula) {
        return Arrays.stream(Symbol.values()).map(symbol -> StringUtils.countMatches(formula, symbol.getLiteral())).reduce(Integer::sum).orElse(0);
    }

    /**
     * 根据计算公式，判断是否存在计算符
     *
     * @param formula 公式
     * @return 判断结果
     */
    public static boolean noSymbol(String formula) {
        Optional<Symbol> any = Arrays.stream(Symbol.values()).filter(symbol -> StringUtils.contains(formula, symbol.getLiteral())).findAny();
        return !any.isPresent();
    }
}
