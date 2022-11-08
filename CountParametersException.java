package ru.exeption.exep003.homework;


public class CountParametersException extends RuntimeException {
    public CountParametersException(int arrayLength, String message) {
        super(arrayLength > 6 ? message + " // You input more than 6 params" : message +" // You input less than 6 params");
    }
}

