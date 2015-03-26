package com.qatang.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author qatang
 * @since 2015-01-28 17:24
 */
public class Test {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CountDownLatch latch;

    public Test(CountDownLatch latch){
        this.latch = latch;
    }

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                latch.countDown();
//                System.out.println("1111");
//            }
//        });

        Test test = new Test(latch);
        test.doit();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public void doit() {
        AAA aaa = new AAA(latch);
        Thread thread2 = new Thread(aaa);

        thread2.start();
    }

    public class AAA implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        private CountDownLatch latch;

        public AAA(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            latch.countDown();
            logger.error("13asdasdasd");
        }
    }
}
