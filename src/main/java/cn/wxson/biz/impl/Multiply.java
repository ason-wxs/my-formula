package cn.wxson.biz.impl;

import cn.wxson.biz.Eval;

import java.math.BigDecimal;

/**
 * Title 乘法
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
public class Multiply implements Eval {

    @Override
    public BigDecimal eval(String first, String second) {
        BigDecimal firstBd = new BigDecimal(first);
        BigDecimal secondBd = new BigDecimal(second);
        return firstBd.multiply(secondBd);
    }
}
