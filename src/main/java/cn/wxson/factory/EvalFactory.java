package cn.wxson.factory;

import cn.wxson.biz.Eval;
import cn.wxson.biz.impl.Add;
import cn.wxson.biz.impl.Divide;
import cn.wxson.biz.impl.Multiply;
import cn.wxson.biz.impl.Subtract;
import cn.wxson.model.Symbol;
import lombok.extern.slf4j.Slf4j;

/**
 * Title 算子工厂
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@Slf4j
public class EvalFactory {

    /**
     * 根据计算符获得计算实例
     *
     * @param symbol 计算符
     * @return 计算实例
     */
    public static Eval create(Symbol symbol) {
        switch (symbol) {
            case ADD:
                return new Add();
            case SUBTRACT:
                return new Subtract();
            case MULTIPLY:
                return new Multiply();
            case DIVIDE:
                return new Divide();
            default:
                log.error("[不存在该计算符] [计算符：{}]", symbol);
                throw new NullPointerException("[不存在的计算符]");
        }
    }
}
