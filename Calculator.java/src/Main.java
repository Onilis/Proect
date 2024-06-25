package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorImpl {

    private static final String DOUBLE_QUOTED_WRAPPED = "\"[^\"]+\"";
    private static final String ALL_OPERATORS = "[+\\-*/]";
    private static final String ADDITION_SUBTRACTION_OPERATORS = ".*[\\-+].*";
    private static final String MULTIPLICATION_DIVISION_OPERATORS = ".*[\\*/].*";
    private static final Set<String> RELEVANT_INTEGERS = new HashSet<>();

    static {
        RELEVANT_INTEGERS.add("1");
        RELEVANT_INTEGERS.add("2");
        RELEVANT_INTEGERS.add("3");
        RELEVANT_INTEGERS.add("4");
        RELEVANT_INTEGERS.add("5");
        RELEVANT_INTEGERS.add("6");
        RELEVANT_INTEGERS.add("7");
        RELEVANT_INTEGERS.add("8");
        RELEVANT_INTEGERS.add("9");
        RELEVANT_INTEGERS.add("10");
    }

    public static void main(String[] args) {
        String expression = generateInputExpression();
        String[] elements = getElements(expression);
        checkValidationInputExpression(elements);
        String input = calculation(elements);
        String resultStr = checkResultStr(input);
        System.out.println(resultStr);
    }

    private static String checkResultStr(String input) {
        return input.length() > 40 ? input.substring(0, 40) + "..." : input;
    }

    private static String generateInputExpression() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static String[] getElements(String expression) {
        String[] elements = new String[3];
        Pattern pattern = Pattern.compile(ALL_OPERATORS);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            elements[1] = matcher.group().trim();
            String[] split = expression.split(Pattern.quote(elements[1]));
            elements[0] = split[0].trim();
            elements[2] = split[1].trim();
        } else {
            throw new RuntimeException();
        }
        return elements;
    }

    private static void checkValidationInputExpression(String[] elements) {
        boolean isLengthString = elements[0].replace("\"", "").length() > 10 || elements[2].replace("\"", "").length() > 10;
        boolean isNotString = !elements[0].matches(DOUBLE_QUOTED_WRAPPED);
        boolean isLengthExpression = elements.length != 3;
        boolean isRelevantStringWithOperator = !elements[1].matches(ADDITION_SUBTRACTION_OPERATORS) && elements[2].matches(DOUBLE_QUOTED_WRAPPED);
        boolean isRelevantInteger = !elements[2].matches(DOUBLE_QUOTED_WRAPPED) && (!RELEVANT_INTEGERS.contains(elements[2]) || !isInteger(elements[2]));
        boolean isRelevantNumberWithOperator = !elements[1].matches(MULTIPLICATION_DIVISION_OPERATORS) && isInteger(elements[2]);
        if (isLengthString || isNotString || isLengthExpression
                || isRelevantStringWithOperator
                || isRelevantInteger
                || isRelevantNumberWithOperator) {
            throw new RuntimeException();
        }
    }

    private static String calculation(String[] elements) {
        return elements[2].matches(DOUBLE_QUOTED_WRAPPED)
                ? strOperations(elements)
                : numOperations(elements);
    }

    private static String numOperations(String[] elements) {
        int num = Integer.parseInt(elements[2]);
        if (elements[1].contains("*")) {
            return String.join("", Collections.nCopies(num, elements[0].replace("\"", "")));
        } else {
            String str = elements[0].replace("\"", "");
            return str.substring(0, str.length() / num);
        }
    }

    private static String strOperations(String[] elements) {
        return elements[1].equals("+")
                ? (elements[0] + elements[2]).replace("\"", "")
                : subtractStr(elements[0].replace("\"", ""), elements[2].replace("\"", ""));
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String subtractStr(String str, String sub) {
        if (str.contains(sub)) {
            return str.replace(sub, "");
        } else {
            return str;
        }
    }
}
