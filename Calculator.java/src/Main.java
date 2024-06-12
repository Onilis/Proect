import java.util.Scanner;

class Calculator2 {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        System.out.println("Введи выражение");

        String exp = scn.nextLine();
        char action = getAction(exp);
        String[] data = getData(exp, action);

        if (action == '*' || action == '/') {
            validateMultiplicationOrDivision(data[1]);
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].replace("\"", "");
        }

        String result = calculateResult(action, data);
        printInQuotes(result);
    }

    static String[] getData(String exp, char action) throws Exception {
        String[] data = new String[0];
        if (exp.length() > 10) {
            throw new Exception("Не более 10");
        }
        for (String datum : data) {
            int number = Integer.parseInt(datum);
            if (number < 1 || number > 10) {
                throw new Exception("Не больше 10");
            }
        }
        switch (action) {
            case '+':
                data = exp.split(" \\+ ");
                break;
            case '-':
                data = exp.split(" - ");
                break;
            case '*':
                data = exp.split(" \\* ");
                break;
            case '/':
                data = exp.split(" / ");
                break;
            default:
                data = new String[0]; // technically unreachable
        }
        return data;
    }


    static char getAction(String exp) throws Exception {
        if (exp.contains(" + ")) {
            return '+';
        } else if (exp.contains(" - ")) {
            return '-';
        } else if (exp.contains(" * ")) {
            return '*';
        } else if (exp.contains(" / ")) {
            return '/';
        } else {
            throw new Exception("Некорректно");
        }
    }

    static void validateMultiplicationOrDivision(String data) throws Exception {
        if (data.contains("\"")) {
            throw new Exception("Строчку делить или умножать нужно только на число");
        }
    }

    static String calculateResult(char action, String[] data) throws Exception {
        String result = "";
        switch (action) {
            case '+':
                result = data[0] + data[1];
                break;
            case '*':
                int multiplier = Integer.parseInt(data[1]);
                for (int i = 0; i < multiplier; i++) {
                    result += data[0];
                }
                break;
            case '-':
                int index = data[0].indexOf(data[1]);
                if (index == -1) {
                    result = data[0];
                } else {
                    result = data[0].substring(0, index) + data[0].substring(index + data[1].length());
                }
                break;
            case '/':
                int newLen = data[0].length() / Integer.parseInt(data[1]);
                result = data[0].substring(0, newLen);
                break;
        }
        return result;
    }

    static void printInQuotes(String text) {
        if (text.length() > 40) {
            System.out.println("\"" + text.substring(0, 40) + "...\"");
        } else {
            System.out.println("\"" + text + "\"");
        }
    }
}