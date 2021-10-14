package com.bjsxt.test;

public class TestThreadLocal2 {
    public static void main(String[] args) {
        for(int i = 0; i < 3; i++){
            // 启动3个线程
            new Thread(new Thread2(100L * (i+1) * 2)).start();
        }
    }
}

class Thread2 implements Runnable{
    long times = 0L;
    public Thread2(long times){
        this.times = times;
    }
    /**
     * 循环10，输出语句。
     */
    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Thread.sleep(times);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 从上下文中获取整数
                Integer i = Context.get();
                // 判断整数是否存在。如果不存在代表第一次使用。
                if (i == null) {
                    i = 0; // 初始化要使用的整数
                    Context.set(i); // 保存到上下文中，后续使用。

                }
                System.out.println(Thread.currentThread().getName() + " - " + i++);
                // 把处理后的整数，保存到上下文。保证后续使用的时候，是正确的数据
                Context.set(i);
                // 判断整数是否达到阈值
                if (i >= 10) {
                    break;
                }
            }
        }finally {
            // 线程执行结束后，删除上下文中的整数
            Context.remove();
        }
    }
}

/**
 * 上下文
 * 和某资源相关的所有数据的集合。
 * 如： PageContext - JSP中的类型，代表和页面相关的所有数据的集合。包括：请求，应答，页面，会话，应用上下文等。
 * 如： ApplicationContext - spring中的类型。代表和应用相关的所有数据的集合。包括：bean对象，controller对象，service对象，代理
 */
class Context{
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static Integer get(){
        return threadLocal.get();
    }
    public static void set(Integer i){
        threadLocal.set(i);
    }
    public static void remove(){
        threadLocal.remove();
    }
}
