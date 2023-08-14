package com.example.guessnumberapp;

public class HintProvider {

    public String provideHint(int number) {
        if (isFibonacci(number)) return "It's a Fibonacci number";
        else if (isPalindrome(number)) return "It's a Palindrome number.";
        else if (isPowerOf2(number)) return "It's a power of 2.";
        else if (isPowerOf3(number)) return "It's a power of 3.";
        else if (number % 5 == 0) return "It is perfectly divisible by 5";
        else if (number % 2 == 0) return "It is an even number";
        else return "It is an odd number";
    }

    private boolean isPerfectSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    private boolean isFibonacci(int num) {
        return isPerfectSquare(5 * num * num + 4) || isPerfectSquare(5 * num * num - 4);
    }

    private boolean isPalindrome(int num) {
        int originalNum = num;
        int reversedNum = 0;

        while (num > 0) {
            int digit = num % 10;
            reversedNum = reversedNum * 10 + digit;
            num /= 10;
        }
        return originalNum == reversedNum;
    }

    private boolean isPowerOf2(int num) {
        return (num > 0) && ((num & (num - 1)) == 0);
    }

    private boolean isPowerOf3(int num) {
        if (num <= 0) return false;

        while (num % 3 == 0) {
            num /= 3;
        }
        return num == 1;
    }
}
