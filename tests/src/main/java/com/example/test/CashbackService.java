package com.example.test;

public class CashbackService {
    public int calculate(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount should be positive");
        }
        if (amount > Integer.MAX_VALUE / 5) {
            throw new IllegalArgumentException("amount too big");
        }
        if (amount > 3000) {
            return 5 * amount / 100;
        }
        return 0;
    }
}
