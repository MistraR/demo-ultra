package com.mistra.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.mistra.demo.service.DemoService.*;

@Controller
public class BasicController {

    @GetMapping("/os-thread")
    @ResponseBody
    public String os(@RequestParam(name = "taskCount") Integer taskCount) throws InterruptedException {
        System.out.println("Testing with traditional threads...");
        long traditionalStartTime = System.currentTimeMillis();
        testTraditionalThreads(taskCount);
        long traditionalEndTime = System.currentTimeMillis();
        System.out.println("Traditional threads took: " + (traditionalEndTime - traditionalStartTime) + " ms");
        return traditionalEndTime - traditionalStartTime + " ms";
    }

    @GetMapping("/visual-thread")
    @ResponseBody
    public String visual(@RequestParam(name = "taskCount") Integer taskCount) throws InterruptedException {
        System.out.println("Testing with virtual threads...");
        long virtualStartTime = System.currentTimeMillis();
        testVirtualThreads(taskCount);
        long virtualEndTime = System.currentTimeMillis();
        System.out.println("Virtual threads took: " + (virtualEndTime - virtualStartTime) + " ms");
        return virtualEndTime - virtualStartTime + " ms";
    }

    @GetMapping("/os-thread-http")
    @ResponseBody
    public String osHttp(@RequestParam(name = "taskCount") Integer taskCount) throws InterruptedException {
        System.out.println("Testing with traditional threads http task...");
        long traditionalStartTime = System.currentTimeMillis();
        testTraditionalThreadsHttp(taskCount);
        long traditionalEndTime = System.currentTimeMillis();
        System.out.println("Traditional threads http task took: " + (traditionalEndTime - traditionalStartTime) + " " + "ms");
        return traditionalEndTime - traditionalStartTime + " ms";
    }

    @GetMapping("/visual-thread-http")
    @ResponseBody
    public String visualHttp(@RequestParam(name = "taskCount") Integer taskCount) throws InterruptedException {
        System.out.println("Testing with virtual threads http task...");
        long traditionalStartTime = System.currentTimeMillis();
        testVirtualThreadsHttp(taskCount);
        long traditionalEndTime = System.currentTimeMillis();
        System.out.println("Virtual threads http task took: " + (traditionalEndTime - traditionalStartTime) + " " + "ms");
        return traditionalEndTime - traditionalStartTime + " ms";
    }
}
