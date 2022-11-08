package ru.exeption.exep003.homework;

public class ParameterFormatException extends RuntimeException{
    public ParameterFormatException(String param) {
        super("Illegal params format " + param);
    }
}
