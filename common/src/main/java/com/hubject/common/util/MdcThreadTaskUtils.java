package com.hubject.common.util;

import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * cpu密集线程池
 */
public class MdcThreadTaskUtils {
    private static MdcThreadPoolTaskExecutor taskExecutor = null;

    static {
        taskExecutor = new MdcThreadPoolTaskExecutor();
        //===cpu密集线程池 设置
        // 核心线程数
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 最大线程数
        taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // 队列最大长度
        taskExecutor.setQueueCapacity(1024);
        // 线程池维护线程所允许的空闲时间(单位秒)
        taskExecutor.setKeepAliveSeconds(120);
        // 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        taskExecutor.initialize();
    }

    public static void execute(Runnable runnable) {
        taskExecutor.execute(runnable);
    }
}
