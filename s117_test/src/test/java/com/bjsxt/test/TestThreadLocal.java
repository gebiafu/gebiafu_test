package com.bjsxt.test;

/**
 * 学习ThreadLocal
 */
public class TestThreadLocal {

    public static void main(String[] args) {
        // 启动多线程
        for(int i = 0; i < 3; i++){
            new Thread(new MyThread()).start();
        }
    }
}

/**
 * 定义线程类型
 * 线程中要提供一个计数器。每个线程内循环10次。每次输出一个字符串。
 */
class MyThread implements Runnable{
    // 当前定义一个ThreadLocal类型的变量。保存每个线程中的计数器。
    // ThreadLocal就是一个Map。 ThreadLocal<T> 相当于  Map<Thread, T>
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    @Override
    public void run() {
        try {
            while (true) {
                // get()方法，是使用当前线程对象作为key查询value的一个方法。
                // get()方法，相当于 map.get(Thread.currentThread());
                Integer i = threadLocal.get();
                if (i == null) {
                    i = 0;
                    // set(T) 方法，相当于 map.put(Thread.currentThread(), T);
                    threadLocal.set(i);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 第" + i++ + "次");
                // 每次使用数据后，只要数据有修改，则一定要使用set方法，覆盖ThreadLocal中的数据。保证线程需要使用的变量是正确的。
                threadLocal.set(i);
                if (i >= 10) {
                    break;
                }
            }
        }finally {
            // 相当于 map.remove(Thread.currentThread());
            threadLocal.remove();
        }
    }
}
