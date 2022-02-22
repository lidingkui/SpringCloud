package com.hubject.common.util;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    @Override
    public void execute(Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(() -> run(runnable, context));
    }

    private void run(Runnable runnable, Map<String, String> context) {
        if (context != null) {
            try {
                MDC.setContextMap(context);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        try {
            runnable.run();
        } finally {
            MDC.clear();
        }
    }
}