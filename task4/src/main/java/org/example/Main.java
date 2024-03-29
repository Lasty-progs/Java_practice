package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args){

        System.out.println(nonRepeatable("paparazzi"));
        System.out.println(generateBrackets(3));
        System.out.println(binarySystem(4));
        System.out.println(alphabeticRow("abcdcb"));
        System.out.println(countSort("vvvvaajaaaaa"));
        System.out.println(convertToNum("five hundred sixty seven"));
        System.out.println(uniqueSubstring("77897898"));
        System.out.println(shortestWay(new int[][]{
                {2, 7, 3},
                {1, 4, 8},
                {4, 5, 9},
        }));
        System.out.println(numericOrder("re6sponsibility Wit1h gr5eat power3 4comes g2reat"));
        System.out.println(switchNums(6274, 71259));

    }

    public static String nonRepeatable(String str) {
        if (str.length() <= 1) {
            return str;
        }
        char lastChar = str.charAt(str.length() - 1);
        String noDuplicate = nonRepeatable(str.substring(0, str.length() - 1));

        if (noDuplicate.indexOf(lastChar) != -1) {
            return noDuplicate;
        } else {
            return noDuplicate + lastChar;
        }
    }

    public static ArrayList<String> generateBrackets(int n) {
        ArrayList<String> massive = new ArrayList<>();
        getCombine(n, n, massive, "");
        return massive;
    }

    public static void getCombine(int left, int right, ArrayList<String> result, String current) {
        if (left == right && right == 0) {
            result.add(current);
            return;
        }

        if (left > 0) {getCombine(left - 1, right, result, current + "(");}
        if (right > left) {getCombine(left, right - 1, result, current + ")");}
    }

    public static List<String> binarySystem(int n) {
        ArrayList<String> massive = new ArrayList<>(2);
        int count = (int) Math.pow(2, n) - 1;

        while (count != 0) {
            boolean error = false;
            String binary = Integer.toBinaryString(count);

            if (binary.length() < n) {
                binary = addNulls(n, binary);
            }

            for (int i = 0; i < binary.length() - 1; i++) {
                if (binary.charAt(i) == binary.charAt(i + 1) && binary.charAt(i) == '0' || massive.contains(binary)) {
                    error = true;
                    break;
                }
            }

            if (!error) {
                massive.add(binary);
            }

            count -= 1;
        }

        return massive.reversed();
    }

    public static String addNulls(int n, String currentBinary) {
        String resultString = currentBinary;
        for (int i = 0; i < n - currentBinary.length(); i++) {
            resultString = "0" + resultString;
        }
        return resultString;
    }

    public static String alphabeticRow(String string) {
        ArrayList<String> massive = new ArrayList<>(string.length());
        String newStr = "";
        int flag = 0;

        for (int i = 0; i < string.length() - 1; i++) {
            char currChar = string.charAt(i);
            char nextChar = string.charAt(i + 1);
            if (newStr.isEmpty()) {
                newStr += currChar;
            }
            if ((nextChar - newStr.charAt(newStr.length() - 1) == 1) && (flag != 2)){
                flag = 1;
                newStr += nextChar;
            } else if((nextChar - newStr.charAt(newStr.length() - 1) == -1) && (flag != 1)){
                flag = 2;
                newStr += nextChar;
            } else {
                flag = 0;
                newStr = "";
            }
            massive.add(newStr);
        }

        int maxLength = 0;

        for (String sequence : massive) {
            if (sequence.length() > maxLength) {
                maxLength = sequence.length();
            }
        }

        for (String sequence : massive) {
            if (sequence.length() == maxLength) {
                newStr = sequence;
            }
        }
        return newStr;
    }

    public static String countSort(String str) {
        ArrayList<String> list = new ArrayList<>();
        String newStr = "";
        String result = "";

        for (int i = 0; i < str.length() - 1; i++) {
            char prevChar = str.charAt(i);
            char nextChar = str.charAt(i + 1);
            if (newStr.isEmpty()) {
                newStr += prevChar;
            }
            if (prevChar == nextChar) {
                newStr += nextChar;
            } else {
                list.add(newStr.charAt(0) + Integer.toString(newStr.length()));
                newStr = "";
            }
            if (i == str.length() - 2) {
                list.add(newStr.charAt(0) + Integer.toString(newStr.length()));
            }
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                String buffer;
                if (list.get(j + 1).charAt(1) < list.get(j).charAt(1)) {
                    buffer = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, buffer);
                } else if (list.get(j + 1).charAt(1) == list.get(j).charAt(1)) {
                    if (list.get(j + 1).charAt(0) > list.get(j).charAt(0)) {
                        buffer = list.get(j);
                        list.set(j, buffer);
                        list.set(j + 1, list.get(j + 1));
                    }
                }
            }
        }

        for (String elem : list) {
            result += elem;
        }

        return result;
    }

    public static int convertToNum(String number) {
        String[] numbers = {
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
                "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen",
                "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety", "hundred"
        };

        int[] values = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 30, 40, 50, 60, 70, 80, 90, 100
        };

        HashMap<String, Integer> numberMap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            numberMap.put(numbers[i], values[i]);
        }

        String[] parts = number.split(" ");
        int result = 0;
        int current = 0;

        for (String part : parts) {
            int value = numberMap.get(part);
            if (value == 100) {
                current *= 100;
            } else {
                current += value;
            }
        }


        return result + current;
    }

    public static String uniqueSubstring(String initSequence) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        String newStr = "";
        int maxLength = 0;

        for (int i = 0; i < initSequence.length(); i++) {
            if (map.containsKey("" + initSequence.charAt(i))) {
                list.add(newStr);
                newStr = "";
                newStr += initSequence.charAt(i);
                for (int j = 1; j < 11; j++) {
                    map.remove(Integer.toString(j));
                }
                map.put("" + initSequence.charAt(i), 1);
            } else {
                newStr += initSequence.charAt(i);
                map.put("" + initSequence.charAt(i), 1);
            }
            if (i == initSequence.length() - 1) {
                list.add(newStr);
            }
        }

        for (String elem : list) {
            if (elem.length() > maxLength) {
                maxLength = elem.length();
            }
        }

        for (String elem : list) {
            if (elem.length() == maxLength) {
                newStr = elem;
                break;
            }
        }

        return newStr;
    }

    public static int shortestWay(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        int[][] newMatrix = new int[m][n];
        newMatrix[0][0] = matrix[0][0];

        for (int i = 1; i < m; i++) {
            newMatrix[i][0] = newMatrix[i - 1][0] + matrix[i][0];
        }
        for (int j = 1; j < n; j++) {
            newMatrix[0][j] = newMatrix[0][j - 1] + matrix[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                newMatrix[i][j] = Math.min(newMatrix[i - 1][j], newMatrix[i][j - 1]) + matrix[i][j];
            }
        }

        return newMatrix[m - 1][n - 1];
    }

    public static String numericOrder(String string) {
        String[] splitString = string.split(" ");
        String[] newString = new String[splitString.length];
        String result = "";

        for (String elem : splitString) {
            for (int i = 0; i < elem.length(); i++) {
                if (elem.charAt(i) >= 48 && elem.charAt(i) <= 57) {
                    if (i == 0) {
                        newString[getIndex("" + elem.charAt(i))] = elem.substring(1);
                    } else {
                        if (i == elem.length() - 1) {
                            newString[getIndex("" + elem.charAt(i))] = elem.substring(0, i);
                        } else {
                            newString[getIndex("" + elem.charAt(i))] = elem.substring(0, i) + elem.substring(i + 1);
                        }
                    }
                }
            }
        }

        for (String elem : newString) {
            result += elem + " ";
        }

        return result;
    }

    public static int getIndex(String charElem) {
        int result = 0;
        for (int i = 0; i <= 10; i++) {
            if (Integer.toString(i).equals(charElem)) {
                result = i;
            }
        }
        return result - 1;
    }

    public static int switchNums(int firstDigit, int secondDigit) {
        ArrayList<Integer> arrayOfFirstDigit = new ArrayList<>();
        ArrayList<Integer> arrayOfSecondDigit = new ArrayList<>();
        int maxDigit = 0;
        int j = 0;

        while (firstDigit != 0) {
            arrayOfFirstDigit.add(firstDigit % 10);
            firstDigit /= 10;
            j += 1;
        }

        j = 0;
        while (secondDigit != 0) {
            arrayOfSecondDigit.add(secondDigit % 10);
            secondDigit /= 10;
            j += 1;
        }

        Collections.sort(arrayOfFirstDigit);
        Collections.reverse(arrayOfFirstDigit);
        Collections.reverse(arrayOfSecondDigit);

        for (int elem : arrayOfSecondDigit) {
            if (!arrayOfFirstDigit.isEmpty()) {
                if (arrayOfFirstDigit.get(0) > elem) {
                    arrayOfSecondDigit.set(arrayOfSecondDigit.indexOf(elem), arrayOfFirstDigit.get(0));
                    arrayOfFirstDigit.remove(0);
                }
            } else {
                break;
            }
        }

        Collections.reverse(arrayOfSecondDigit);

        for (int i = 0; i < arrayOfSecondDigit.size(); i++) {
            maxDigit += arrayOfSecondDigit.get(i) * (int) Math.pow(10, i);
        }

        return maxDigit;
    }
}