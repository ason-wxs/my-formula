package cn.wxson.test;

import cn.wxson.biz.Parser;
import cn.wxson.biz.impl.ParserImpl;

import java.math.BigDecimal;

/**
 * Title 测试类
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
public class Domain {

    public static void main(String[] arg) {
        String formula = "  [[20*2]+[[1-3] *2] ] / 4 ";
        Parser parser = new ParserImpl();
        BigDecimal result = parser.parse(formula);
        System.out.println(result.toString());
    }
}
