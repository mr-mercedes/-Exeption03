package ru.exeption.exep003.homework;

public class StringFormatException extends RuntimeException{
    public StringFormatException(String[] params) {
        for (String param : params) {
            if (!isWord(param)) {
                throw new RuntimeException("Parameter " + param + " isn't Name format");
            }
        }
    }

    private static boolean isWord(String params) throws RuntimeException{
        if (params.length() < 2) return false;
        for (int i = 0; i < params.length(); i++) {
            char ch = params.charAt(i);
            if (!Character.isLetter(ch)){
                return false;
            }
        }
        return true;
    }
}
