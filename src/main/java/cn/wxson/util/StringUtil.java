package cn.wxson.util;

import cn.wxson.model.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * Title 字符串操作
 *
 * @author Ason(18078490)
 * @date 2020-07-24
 */
@Slf4j
public final class StringUtil {

    /**
     * 分隔符
     */
    public static final String[] SRC_BRACKETS = {Constant.BRACKETS_START, Constant.BRACKETS_END};
    /**
     * 清洗分隔符为空的集合
     */
    public static final String[] DESC_BRACKETS = {StringUtils.EMPTY, StringUtils.EMPTY};
    /**
     * 字符：[
     */
    public static final char SC = Constant.BRACKETS_START.toCharArray()[0];
    /**
     * 字符：]
     */
    public static final char EC = Constant.BRACKETS_END.toCharArray()[0];

    /**
     * 从开始分隔符下标开始，获取与其对应的结束分隔符索引
     * 这里使用stack结构，便于标识多重分隔符号场景下，找到目标结束符
     *
     * @param formula 公式
     * @param index   开始分割符的下标，即"["的下标
     * @return 与开始分隔符"["相对应的结束分隔符"]"的下标
     */
    public static int index(String formula, int index) {
        Stack<Integer> stack = new Stack<>();
        char[] chars = formula.toCharArray();
        for (int i = index + 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == SC) {
                stack.push(i);
            } else if (c == EC) {
                if (stack.empty()) {
                    return i;
                } else {
                    stack.pop();
                }
            }
        }
        return -1;
    }

    /**
     * 清洗字符串
     *
     * @param value 字符串值
     * @return 清洗结果
     */
    public static String clean(String value) {
        return trim(replace(value));
    }

    /**
     * 去除字符串两端空字符
     *
     * @param value 字符串值
     * @return 结果
     */
    public static String trim(String value) {
        return StringUtils.trim(value);
    }

    /**
     * 替换字符串中的分隔符为空
     *
     * @param value 字符串值
     * @return 替换结果
     */
    public static String replace(String value) {
        return StringUtils.replaceEach(value, SRC_BRACKETS, DESC_BRACKETS);
    }
}

