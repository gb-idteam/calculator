package ru.systemairac.calculator.exception;

public class HumidifierNotFoundException extends RuntimeException {
    public HumidifierNotFoundException(String message){
        super(message);
    }
}
