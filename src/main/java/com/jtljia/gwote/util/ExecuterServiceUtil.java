package com.jtljia.gwote.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuterServiceUtil {
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static ExecutorService getExecutorService() {
        if (executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(5);
        }
        return executorService;
    }
}
