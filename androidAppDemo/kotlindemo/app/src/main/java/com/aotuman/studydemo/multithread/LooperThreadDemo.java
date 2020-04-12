package com.aotuman.studydemo.multithread;

public class LooperThreadDemo implements IRunTest {

    LooperThread looperThread = new LooperThread();

    @Override
    public void runTest() {
        looperThread.start();
        looperThread.looper.setTask(new Runnable() {
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
        looperThread.looper.quit();
    }

    class LooperThread extends Thread{
        Looper looper = new Looper();

        @Override
        public void run() {
            looper.loop();
        }
    }

    class Looper {
        private Runnable task;
        private boolean quit;

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        synchronized void quit() {
            this.quit = true;
        }

        public void loop() {
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
