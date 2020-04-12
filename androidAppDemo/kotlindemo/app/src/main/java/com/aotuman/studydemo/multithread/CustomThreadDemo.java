package com.aotuman.studydemo.multithread;

public class CustomThreadDemo implements IRunTest {

    CustomThread customThread = new CustomThread();

    @Override
    public void runTest() {
        customThread.start();
        customThread.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("run run run");
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customThread.quit();
    }

    class CustomThread extends Thread {

        private Runnable task;
        private boolean quit;

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        synchronized void quit() {
            this.quit = true;
        }

        @Override
        public void run() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }

}
