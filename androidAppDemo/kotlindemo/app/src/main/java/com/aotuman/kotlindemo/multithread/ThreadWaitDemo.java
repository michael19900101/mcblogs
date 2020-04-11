package com.aotuman.kotlindemo.multithread;

public class ThreadWaitDemo implements IRunTest {

    private String content;

    private synchronized void initContent() {
        content = "michaeljordan";
        notifyAll();
    }

    private synchronized void printContent() {
        while (content == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("thread name:%s,content:%s%n", Thread.currentThread().getName(), content);
    }

    @Override
    public void runTest() {
        // 线程1先打印
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printContent();
            }
        });
        thread1.setName("thread1");
        thread1.start();

        // 线程2后打印
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printContent();
            }
        });
        thread2.setName("thread2");
        thread2.start();

        // 线程3后初始化
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initContent();
            }
        });
        thread3.start();
    }
}
