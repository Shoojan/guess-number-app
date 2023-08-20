package com.example.guessnumberapp.utils;

public class RandomNumberGenerator {

    public static int generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("Number:" + randomNumber);
        return randomNumber;
    }
}
