package vkernel.api;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMath {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[0-9\\.+-/*()= ]+");
    private static final Map<String, Integer> OPT_PRIORITY_MAP = new HashMap<String, Integer>() {
        {
            put("(", 0);
            put("+", 2);
            put("-", 2);
            put("*", 3);
            put("/", 3);
            put(")", 7);
            put("=", 20);
        }
    };

    public static boolean isNumeric(String str) {
        return Pattern.compile("-?[0-9]+.?[0-9]*").matcher(str).matches();
    }

    public static double eval(String expression) {
        if (expression == null || expression.trim().equals(""))
            throw new IllegalArgumentException("表达式不能为空");
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches())
            throw new IllegalArgumentException("表达式含有非法字符");
        Stack<String> optStack = new Stack<>();
        Stack<BigDecimal> numStack = new Stack<>();
        StringBuilder curNumBuilder = new StringBuilder(16);
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c != ' ') {
                if ((c >= '0' && c <= '9') || c == '.') {
                    curNumBuilder.append(c);
                } else {
                    if (curNumBuilder.length() > 0) {
                        numStack.push(new BigDecimal(curNumBuilder.toString()));
                        curNumBuilder.delete(0, curNumBuilder.length());
                    }
                    String curOpt = String.valueOf(c);
                    if (optStack.empty())
                        optStack.push(curOpt);
                    else {
                        if (curOpt.equals("("))
                            optStack.push(curOpt);
                        else if (curOpt.equals(")"))
                            directCalc(optStack, numStack, true);
                        else if (curOpt.equals("=")) {
                            directCalc(optStack, numStack, false);
                            return numStack.pop().doubleValue();
                        } else compareAndCalc(optStack, numStack, curOpt);
                    }
                }
            }
        }
        if (curNumBuilder.length() > 0)
            numStack.push(new BigDecimal(curNumBuilder.toString()));
        directCalc(optStack, numStack, false);
        return numStack.pop().doubleValue();
    }


    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

    private static void compareAndCalc(Stack<String> optStack, Stack<BigDecimal> numStack, String curOpt) {
        String peekOpt = optStack.peek();
        int priority = getPriority(peekOpt, curOpt);
        if (priority == -1 || priority == 0) {
            String opt = optStack.pop();
            BigDecimal num2 = numStack.pop();
            BigDecimal num1 = numStack.pop();
            BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);
            numStack.push(bigDecimal);
            if (optStack.empty())
                optStack.push(curOpt);
            else
                compareAndCalc(optStack, numStack, curOpt);
        } else
            optStack.push(curOpt);
    }

    private static void directCalc(Stack<String> optStack, Stack<BigDecimal> numStack, boolean isBracket) {
        String opt = optStack.pop();
        BigDecimal num2 = numStack.pop();
        BigDecimal num1 = numStack.pop();
        BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);
        numStack.push(bigDecimal);
        if (isBracket) {
            if ("(".equals(optStack.peek()))
                optStack.pop();
            else
                directCalc(optStack, numStack, isBracket);
        } else {
            if (!optStack.empty()) directCalc(optStack, numStack, isBracket);
        }
    }

    private static BigDecimal floatingPointCalc(String opt, BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        BigDecimal resultBigDecimal = new BigDecimal(0);
        switch (opt) {
            case "+":
                resultBigDecimal = bigDecimal1.add(bigDecimal2);
                break;
            case "-":
                resultBigDecimal = bigDecimal1.subtract(bigDecimal2);
                break;
            case "*":
                resultBigDecimal = bigDecimal1.multiply(bigDecimal2);
                break;
            case "/":
                resultBigDecimal = bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_DOWN);
                break;
            default:
                break;
        }
        return resultBigDecimal;
    }

    private static int getPriority(String opt1, String opt2) {
        return OPT_PRIORITY_MAP.get(opt2) - OPT_PRIORITY_MAP.get(opt1);
    }
}