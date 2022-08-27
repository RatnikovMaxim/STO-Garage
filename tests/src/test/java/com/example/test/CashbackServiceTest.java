package com.example.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CashbackServiceTest {
    @Test
    void shouldCalculateForAmountBiggerThan3000() {
        // A-A-A
        // 1. Готовим окружение (Arrange)
        final CashbackService cashbackService = new CashbackService();
        final int expected = 5000;
        final int amount = 100_000;
        // 2. Целевые действия (Act)
        final int actual = cashbackService.calculate(amount);
        // 3. Сравнение факт vs ожидаемое (Assert)
        assertEquals(expected, actual);
    }

    @Test
    void shouldCalculateZeroForAmountLowerThan3000() {
        // A-A-A
        // 1. Готовим окружение (Arrange)
        final CashbackService cashbackService = new CashbackService();
        final int expected = 0;
        final int amount = 2500;
        // 2. Целевые действия (Act)
        final int actual = cashbackService.calculate(amount);
        // 3. Сравнение факт vs ожидаемое (Assert)
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        // 1. Готовим окружение
        final CashbackService cashbackService = new CashbackService();
        final int amount = -1000;
        // 2. Целевые действия
        assertThrows(IllegalArgumentException.class, () -> cashbackService.calculate(amount));
    }

    @Test
    void shouldThrowExceptionForOverflowAmount() {
        // 1. Готовим окружение
        final CashbackService cashbackService = new CashbackService();
        final int amount = Integer.MAX_VALUE / 5 + 1;
        // 2. Целевые действия
        assertThrows(IllegalArgumentException.class, () -> cashbackService.calculate(amount));
    }
}