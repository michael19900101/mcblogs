package com.aotuman.studydemo.multithread;

public class ThreadInterruptDemo implements IRunTest {
    @Override
    public void runTest() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (Thread.interrupted()) {
                        System.out.println("子线程 isInterrupted");
                        return;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("子线程在sleep, 外面有人（主线程）将我中断interrupt, 如果子线程不return, 后面的逻辑会继续跑");
                        e.printStackTrace();
                        return;
                    }
                    System.out.println(i);
                }

            }
        });
        thread.start();

        // 先让主线程睡眠0.5s, 再去中断子线程
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // stop方法是一种"恶意" 的中断,一旦执行stop方法,即终止当前正在运行的线程,不管线程逻辑是否完整,这是非常危险的.
//        thread.stop();

        // 单独调用这个方法并不能中断子线程，只是打了一个中断状态的标记
        thread.interrupt();
    }
}
