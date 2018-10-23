package com.xz.dripping.controller.thread;

import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import java.util.concurrent.*;

/**
 * dripping
 */

public class ThreadExecutorFactory extends ScheduledExecutorFactoryBean {
    private static final long serialVersionUID = 1L;

    @Override
    protected ScheduledExecutorService createExecutor(int poolSize, ThreadFactory threadFactory,
                                                      RejectedExecutionHandler rejectedExecutionHandler) {
        return new ScheduledThreadPoolExecutor(poolSize, threadFactory, rejectedExecutionHandler) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (t == null && r instanceof Future) {
                    Future<?> future = (Future<?>) r;
                    try {
                        future.get();
                    } catch (Throwable e) {
                        t = e;
                    }
                }
                if (t != null) {
                    logger.error("Thread Execute Exception:", t);
                }
            }
        };
    }
}
