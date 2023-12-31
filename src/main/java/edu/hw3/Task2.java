package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Task2 {
    @SuppressWarnings("MissingSwitchDefault")
    public static List<String> clusterize(String string) throws IllegalArgumentException {
        StringValidator validator = new StringValidator();
        validator.validateString(string);
        int startIndex = 0;
        Stack<Character> stack = new Stack<>();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            char symbol = string.charAt(i);

            switch (symbol) {
                case '(' -> stack.push(symbol);
                case ')' -> stack.pop();
            }
            if (stack.isEmpty()) {
                result.add(string.substring(startIndex, i + 1));
                startIndex = i + 1;
            }
        }
        return result;
    }

    private class StringValidator {
        private int leftBracketCounter = 0;
        private int rightBracketCounter = 0;

        private boolean checkSymbol(char symbol) {
            switch (symbol) {
                case '(' -> leftBracketCounter++;
                case ')' -> {
                    if (rightBracketCounter >= leftBracketCounter) {
                        return false;
                    }
                    rightBracketCounter++;
                }
                default -> {
                    return false;
                }
            }
            return true;
        }

        private void validateString(String string) throws IllegalArgumentException {
            if (string.isEmpty()) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < string.length(); i++) {
                char symbol = string.charAt(i);
                if (!checkSymbol(symbol)) {
                    throw new IllegalArgumentException();
                }
            }
            if (leftBracketCounter != rightBracketCounter) {
                throw new IllegalArgumentException();
            }
        }
    }
}
