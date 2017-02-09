package com.scannorone.lab03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumbers {

    private List<Integer> primes;

    public PrimeNumbers(int n) {
        primes = getPrimeNumbers(n);
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    private List<Integer> getPrimeNumbers(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2, ei = (int) Math.sqrt(n) + 1; i <= ei; i++) {
            for (int j = i * i; j <= n; j += i) {
                isPrime[j] = false;
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if (isPrime[i]) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return primes.toString();
    }
}
