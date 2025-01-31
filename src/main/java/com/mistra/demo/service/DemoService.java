package com.mistra.demo.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoService implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
//        while (true) {
//            Thread.sleep(5000);
//            threadTest();
//        }
//        threadTest();
    }

    public static void main(String[] args) throws InterruptedException {
        threadTest();
    }

    public static void threadTest() throws InterruptedException {
        // 任务数量
        int taskCount = 20_000;

        // 测试传统线程
        System.out.println("Testing with traditional threads...");
        long traditionalStartTime = System.currentTimeMillis();
        testTraditionalThreads(taskCount);
        long traditionalEndTime = System.currentTimeMillis();
        System.out.println("Traditional threads took: " + (traditionalEndTime - traditionalStartTime) + " ms");

        // 测试虚拟线程
        System.out.println("\nTesting with virtual threads...");
        long virtualStartTime = System.currentTimeMillis();
        testVirtualThreads(taskCount);
        long virtualEndTime = System.currentTimeMillis();
        System.out.println("Virtual threads took: " + (virtualEndTime - virtualStartTime) + " ms");

        // 时间差比较
        System.out.println("\nTime difference: " + ((traditionalEndTime - traditionalStartTime) - (virtualEndTime - virtualStartTime)) + " ms");
    }

    public static void testTraditionalThreads(int taskCount) throws InterruptedException {
        Thread[] threads = new Thread[taskCount];

        for (int i = 0; i < taskCount; i++) {
            threads[i] = new Thread(() -> performTask());
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void testVirtualThreads(int taskCount) throws InterruptedException {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());

        for (int i = 0; i < taskCount; i++) {
            executor.execute(() -> performTask());
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static void performTask() {
        // 模拟一个简单的计算任务
        long sum = 0;
        for (int i = 0; i < 1_000; i++) {
            sum += i;
        }
    }
}
