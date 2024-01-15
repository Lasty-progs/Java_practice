package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.util.Arrays.sort;

public class Main {
    public static void main(String[] args) {
        System.out.println(hiddenAnagram("My world evolves in a beautiful space called Tesh.", "sworn love lived"));

        System.out.println(nicoCipher("mubashirhassan", "crazy"));
        System.out.println(nicoCipher("iloveher", "612345"));

        System.out.println(collect("intercontinentalisationalism", 6));

        int[] b = twoProduct(new int[] {1, 2, 3, 9, 4, 5, 15}, 45);
        for (int i : b) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();

        int[] c = isExact(6);
        for (int i : c) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();


        System.out.println(fractions("3.(142857)"));


        System.out.println(pilish_string("33314444"));
        System.out.println(pilish_string("TOP"));
        System.out.println(pilish_string("X"));


        System.out.println(generateNonconsecutive("3 + 5 * (6 / 2)"));

        System.out.println(isValid("abcdefghhgfedecba"));


        System.out.println(findLCS("HABRAHABR", "HARBOUR"));
    }
    //number1
    public static String hiddenAnagram(String str1, String str2) {
        str1 = str1.toLowerCase();
        str1 = str1.replaceAll("[^a-z]", "");
        str2 = str2.toLowerCase();
        str2 = str2.replaceAll("[^a-z]", "");
        for (int i = 0; i <= str1.length() - str2.length(); i++) {
            String str = str1.substring(i, i + str2.length());
            if (isAnagram(str2, str)) {
                return str;
            }
        }
        return "not found";
    }
    public static boolean isAnagram(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return String.valueOf(chars1).equals(String.valueOf(chars2));
    }
    //number2
    public static ArrayList<String> collect(String str, int num) {
        int count = str.length() / num;
        char[] chars = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(String.valueOf(Arrays.copyOfRange(chars, i*num, i*num+num)));
        }
        return list;
    }

    //number3
    public static String nicoCipher(String message, String key){
        int[] order = getOrder(key);
        int lnMes = message.length();
        int lnKey = key.length();
        String out = "";

        for(int i = lnKey - (lnMes % lnKey); i > 0; i--){
            message = message.concat(" ");
        }

        char[] mes = message.toCharArray();
        char[] temp = new char[lnKey];

        for(int i = 0; i <= lnMes / lnKey; i++){
            for(int j = i * lnKey; j < (i+1)*lnKey; j++){
                temp[order[j%lnKey]] = mes[j];
            }
            for(char elem:temp){
                out = out.concat(Character.toString(elem));
            }
        }

        return out;
    }

    public static int[] getOrder(String key){
        int[] order = new int[key.length()];
        char[] charKey = key.toCharArray();
        char[] sorted = key.toCharArray();
        sort(sorted);
        for(int i = 0; i < charKey.length; i++){
            for(int j = 0; j < sorted.length; j++){
                if(sorted[j] == charKey[i]){
                    order[i] = j;
                }
            }
        }
        return order;
    }
    //number4
    public static int[] twoProduct(int[] array, int num) {
        int gap = 2^31-1;
        int[] out = new int[2];
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] * array[j] == num) {
                    if (j - i < gap) {
                        out[1] = array[j];
                        out[0] = array[i];
                        gap = j - i;
                    }
                }
            }
        }
        if (out[0] == 0) {
            return new int[0];
        } else {
            return out;
        }
    }
    //number5
    public static int[] isExact(int num) {
        if (num == 2) {
            return new int[] {2, 2};
        }
        for (int i = num / 2; i > 2; i--) {
            if (num % i == 0) {
                int[] array = isExact(num/ i);
                if (Arrays.equals(array, new int[0])) {
                    continue;
                }
                if (array[1] == i - 1) {
                    return new int[] {num, i};
                }
            }
        }
        return new int[0];
    }
    //number6
    public static String fractions(String str) {
        int[] array = new int[3];
        String[] split = str.split("\\.");
        array[0] = Integer.parseInt(split[0]);
        StringBuilder strNum1 = new StringBuilder();
        StringBuilder strNum2 = new StringBuilder();
        char[] chars = split[1].toCharArray();
        boolean flag = true;
        for (int i = 0; i < chars.length; i++) {
            if (flag) {
                if (chars[i] != '(') {
                    strNum1.append(chars[i]);
                } else {
                    flag = false;
                    if (!strNum1.isEmpty()) {
                        array[1] = Integer.parseInt(strNum1.toString());
                    }
                }
            } else {
                if(chars[i] != ')') {
                    strNum2.append(chars[i]);
                } else {
                    if (!strNum2.isEmpty()) {
                        array[2] = Integer.parseInt(strNum2.toString());
                    }
                    break;
                }
            }
        }
        String denominatorStr = "9".repeat(strNum2.length()) +
                "0".repeat(strNum1.length());
        int denominator = Integer.parseInt(denominatorStr);
        int numerator = Integer.parseInt(strNum1.append(strNum2).toString()) - array[1];
        int[] fraction = simplifyFraction(numerator, denominator);
        fraction[0] += array[0] * fraction[1];
        return fraction[0] + "/" + fraction[1];
    }

    private static int[] simplifyFraction(int numerator, int denominator) {
        for(;;) {
            int nod = findNOD(denominator, numerator);
            if (nod > 1) {
                denominator /= nod;
                numerator /= nod;
            } else {
                return new int[] {numerator, denominator};
            }
        }
    }

    private static int findNOD(int num1, int num2) {
        int max = Integer.max(num1, num2);
        for (int i = max / 2; i > 1; i--) {
            if (num1 % i == 0 && num2 % i == 0) {
                return i;
            }
        }
        return 1;
    }
    //number7
    public static String pilish_string (String str) {
        ArrayList<String> list = new ArrayList<>();
        double pi = Math.PI;
        String strPi = String.valueOf(pi);
        char[] chars = strPi.toCharArray();
        char[] chars2 = str.toCharArray();
        int startInd = 0;
        for (char i : chars) {
            if (i != '.') {
                int length = Integer.parseInt(String.valueOf(i));
                int counter = 0;
                StringBuilder stB = new StringBuilder();
                if (startInd >= chars2.length) {
                    break;
                }
                for (int j = startInd; j < startInd + length; j++) {
                    if (j < chars2.length) {
                        stB.append(chars2[j]);
                    } else {
                        int lenRep = length - counter;
                        stB.append(stB.substring(stB.length() - 1).repeat(lenRep));
                        break;
                    }
                    counter += 1;
                    if (counter == length) {
                        break;
                    }
                }
                list.add(stB.toString());
                startInd += Integer.parseInt(String.valueOf(i));
            }
        }
        StringBuilder out = new StringBuilder();
        for (String i : list) {
            out.append(" ").append(i);
        }
        return out.substring(1);
    }

    //number8

    public static String generateNonconsecutive(String str) {
        str = str.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ");
        String[] array = str.split(" ");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        while (list.contains("")) {
            list.remove("");
        }
        list = evaluateExpression(list);

        return String.valueOf(list.get(0));
    }

        private static ArrayList<String> evaluateExpression(ArrayList<String> list) {
            while (list.contains("(")) {
                int openIndex = list.lastIndexOf("(");
                int closeIndex = -1;
                for (int i = openIndex + 1; i < list.size(); i++) {
                    if (list.get(i).equals(")")) {
                        closeIndex = i;
                        break;
                    }
                }
                ArrayList<String> subList = new ArrayList<>(list.subList(openIndex + 1, closeIndex));

                String result = evaluateExpression(subList).get(0);
                replaceSubList(list, result, openIndex, closeIndex);
            }

            evaluateOperation(list, "*");
            evaluateOperation(list, "/");

            evaluateOperation(list, "+");
            evaluateOperation(list, "-");

            return list;
        }

        private static void replaceSubList(ArrayList<String> list, String replaceStr, int firstInd, int lastInd) {
            list.subList(firstInd, lastInd + 1).clear();
            list.add(firstInd, replaceStr);
        }

        private static void evaluateOperation(ArrayList<String> list, String operator) {
            while (list.contains(operator)) {
                int index = list.indexOf(operator);

                if (operator.equals("-") && (index == 0 || list.get(index - 1).equals("("))) {
                    double operand = Double.parseDouble(list.get(index + 1));
                    double result = -operand;
                    list.remove(index);
                    list.remove(index);
                    list.add(index, String.valueOf(result));
                } else {
                    double first = Double.parseDouble(list.get(index - 1));
                    double second = Double.parseDouble(list.get(index + 1));
                    double result = 0;

                    switch (operator) {
                        case "*":
                            result = first * second;
                            break;
                        case "/":
                            if (second != 0) {
                                result = first / second;
                            } else {
                                throw new ArithmeticException("Деление на ноль!");
                            }
                            break;
                        case "+":
                            result = first + second;
                            break;
                        case "-":
                            result = first - second;
                            break;
                    }

                    replaceSubList(list, String.valueOf(result), index - 1, index + 1);
                }
            }
        }
    //number 9
    public static String isValid(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        char[] chars = str.toCharArray();
        for (char i : chars) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        Integer[] nums = map.values().toArray(new Integer[0]);
        Arrays.sort(nums);
        boolean flag = true;
        Integer first = nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            if (!first.equals(nums[i])) {
                flag = false;
                break;
            }
        }
        if (!nums[nums.length - 1].equals(first + 1) && !nums[nums.length - 1].equals(first)) {
            flag = false;
        }
        return flag ? "YES" : "NO";
    }
    //number10
    public static String findLCS(String str1, String str2) {
        int[][] array = new int[str1.length() + 1][str2.length() + 1];
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        for (int i = 0; i < chars1.length ; i++) {
            for (int j  = 0; j < chars2.length; j++) {
                if (chars1[i] == chars2[j]) {
                    array[i+1][j+1] = array[i][j] + 1;
                } else {
                    array[i+1][j+1] = Integer.max(array[i][j+1], array[i+1][j]);
                }
            }
        }
        StringBuilder out = new StringBuilder();
        int x = chars1.length;
        int y = chars2.length;
        while (x > 0 && y > 0) {
            if (chars1[x - 1] == chars2[y - 1]) {
                out.append(chars1[x - 1]);
                x -= 1;
                y -= 1;
            } else if (array[x-1][y] > array[x][y-1]) {
                x -= 1;
            } else {
                y -= 1;
            }
        }
        return out.reverse().toString();
    }
}
