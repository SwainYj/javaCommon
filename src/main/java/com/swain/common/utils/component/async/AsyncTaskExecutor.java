package com.swain.common.utils.component.async;

import com.swain.common.utils.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class AsyncTaskExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTaskExecutor.class);

    @Async("AsyncTask")
    public Future executor(AsyncTaskConstructor asyncTaskGenerator) {
        try {
            asyncTaskGenerator.async();
        } catch (Exception e) {
            // 数据库操作异常会被AOP捕获，该处打印null
            LOGGER.error(e.getMessage());
        }
        return new AsyncResult(true);
    }

    @Async("AsyncTask")
    public void executor(AsyncTaskConstructor asyncTaskGenerator, String message) {
        String taskId = StringUtils.getUUID();
        LOGGER.info("Async Task {} Start", taskId);
        long startTime = System.currentTimeMillis();
        try {
            asyncTaskGenerator.async();
        } catch (Exception e) {
            // 数据库操作异常会被AOP捕获，该处打印null
            LOGGER.error(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Async Task {} Cost {}ms {}", taskId, endTime - startTime, message);
    }

    public void waitProcessingOver(List<Future> list) {
        for (Future future : list) {
            try {
                future.get();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
