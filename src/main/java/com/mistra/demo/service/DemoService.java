package com.mistra.demo.service;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mistra.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoService implements CommandLineRunner {

    private static String cookie = "cookiesu=491718892038165; device_id=54bd24038ce768b7fbf05357f46b956c; smidV2=20240620220105fec74d0b83d5a4c9c7bc046d2cd1f135006f06bfccac63f90; s=ax11pyztyy; .thumbcache_f24b8bbe5a5934237bbc0eda20c1b6e7=uh3DueayQ7VeXXxPNUuFEabGxJ85jTMUPfvZMZmnwcJUhkqZgGCxWP6TUh/5KzblyP8E4VOskRg0rFjBb4t2Wg%3D%3D; remember=1; xq_a_token=ff70b716eea275de67d0b9f36e8173373bea6fb0; xqat=ff70b716eea275de67d0b9f36e8173373bea6fb0; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOjU3MzM3MDcyNjEsImlzcyI6InVjIiwiZXhwIjoxNzM5OTI5MDgyLCJjdG0iOjE3Mzc1MDIzODY3NzQsImNpZCI6ImQ5ZDBuNEFadXAifQ.TsDe4NgMUyhrvZx8t1XmaYVpkn5Hh9gWaJpouA8OILn8VRlhJW40Z-pVolK0Uva0uWKQJRQ_tm5BIo-bkJASi5UbbzvgY8aPP5d1JZxx8SsGEKO7kmaww2oJGB4HD52ad6Leq8y-HxzHfwcbD20PgRbJnUn1UJ-11OcgspxG7l3ZfHLV6cLWgX_EtmSu1h74sy4d6MossUyxpbFyv92FRDiG0ZmDDVOjZQieubfMY-kmP-88mIXgtE4fqMFBPxm1fZ9lBYgki3rSDAvCE9yhzun9BiXd7hzEWMWLTKKbJGI1o8yBwbcGy4PoMqL0MFodW-WxVMYVGMycEVP-CC4Xkw; xq_r_token=dd930de96c03fb55b74914bf9d19ee17d2762d33; xq_is_login=1; u=5733707261; Hm_lvt_1db88642e346389874251b5a1eded6e3=1738117887,1738149203,1738207335,1738291103; HMACCOUNT=F60A36CF52A921AE; acw_tc=2760826317383012530728408ebe8861035782f3d6f248fb0d8cd8ffdbd1ff; is_overseas=0; ssxmod_itna=CqmxcDBD2D97KxBPxB4DKfW=DuGBF3eDg0rDiwbmf4Ds3KrDSxGKidDqxBWmZo+poFt7grdqFBj8exdXjDlPTvCt=ApuGbDCPGnDBIEaQ+DYY1Dt4DTD34DYDiEQGIDieDFCqi3DhxGQD3qGy/8ND0R6ej0=DI4iDmLdDx7UBjDDljiTd77GSD0YDb26SSSrDx7QDAwwSWixyD0UoxBdqA1rDGL9k6YXS8Zcikx=xPeGuDG=D1qGm/4a3+wUS41+T8xpYn4ho8hqt30GTt2+r4SxqWBe4SlmpixErGOGa99zdDDfG4bxxD==; ssxmod_itna2=CqmxcDBD2D97KxBPxB4DKfW=DuGBF3eDg0rDiwbm4ikjFDl24QFQ08D+OoD=; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1738301294";

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
            threads[i] = new Thread(DemoService::performTask);
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
            executor.execute(DemoService::performTask);
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

    public static void testVirtualThreadsHttp(int taskCount) throws InterruptedException {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());
        for (int i = 0; i < taskCount; i++) {
            executor.execute(DemoService::performTaskHttp);
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static void performTaskHttp() {
        String url = "https://stock.xueqiu.com/v5/stock/quote.json?symbol=SZ300308&extend=detail" +
                "&md5__1632=n4RxRD9DuD20YGKi%3DD%2FD0iOGkW8dr";
        url = "https://stock.xueqiu.com/v5/stock/history/trade" +
                ".json?symbol=SZ300308&count=10&md5__1632=n4%2BxuDBDnGitGQG8Q%3DDsD7fe0%3DouZ2Y4CkTTD";
        String body = HttpUtil.getHttpGetResponseString(url, cookie);
        JSONObject data = JSON.parseObject(body).getJSONObject("data");
        if (Objects.nonNull(data) && Objects.nonNull(data.getJSONArray("items"))) {
            log.info("{}", data.getJSONArray("items").get(0));
        }
    }

    public static void testTraditionalThreadsHttp(int taskCount) throws InterruptedException {
        Thread[] threads = new Thread[taskCount];

        for (int i = 0; i < taskCount; i++) {
            threads[i] = new Thread(DemoService::performTaskHttp);
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
