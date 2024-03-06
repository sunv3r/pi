package org.example;

import java.util.Random;
public class Main {
    public static void main(String[] args) {
        int numThreads = 4;
        long numIterations = 100000000;
        System.out.println("Procesing...");

        Random random = new Random();

        Thread[] threads = new Thread[numThreads];
        PIWorker[] workers = new PIWorker[numThreads];
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new PIWorker(numIterations / numThreads, random);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        double sum = 0;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                sum += workers[i].getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double pi = 4 * sum / numIterations;

        System.out.println("PI = " + pi);
    }
}

class PIWorker implements Runnable {
    private long numIterations;
    private Random random;
    private double result;

    public PIWorker(long numIterations, Random random) {
        this.numIterations = numIterations;
        this.random = random;
    }

    public double getResult() {
        return result;
    }

    public void run() {
        long numInsideCircle = 0;
        for (long i = 0; i < numIterations; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x*x + y*y <= 1) {
                numInsideCircle++;
            }
        }
        result = (double) numInsideCircle / numIterations;
    }
}

