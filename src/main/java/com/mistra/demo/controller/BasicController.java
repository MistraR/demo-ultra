package com.mistra.demo.controller;

import static com.mistra.demo.service.DemoService.testTraditionalThreads;
import static com.mistra.demo.service.DemoService.testVirtualThreads;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
