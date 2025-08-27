package org.woven.notification.demo.service;

import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Service
@Log
public class SchedulerService {
    private final TaskScheduler scheduler;
    private ScheduledFuture<?> future;

    public SchedulerService() {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
        threadPool.setPoolSize(2);
        threadPool.setThreadNamePrefix("ReminderScheduler-");
        threadPool.initialize();
        this.scheduler = threadPool;
    }

    public void startReminderTask(Runnable task, long delayMillis) {
        future = scheduler.schedule(task, new Date(System.currentTimeMillis() + delayMillis));
    }

    public void stopReminderTask() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }
}
