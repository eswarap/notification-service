package org.woven.notify.svc.service;

import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

@Service
@Log
public class SchedulerService {
    private final TaskScheduler scheduler;
    private ScheduledFuture<?> future;
    private final Queue<Runnable> pendingTasks = new ConcurrentLinkedQueue<>();


    public SchedulerService() {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
        threadPool.setPoolSize(2);
        threadPool.setThreadNamePrefix("ReminderScheduler-");
        threadPool.initialize();
        this.scheduler = threadPool;
    }

    public void addTaskToQueue(Runnable task) {
        pendingTasks.offer(task);
    }

    private boolean hasPendingTasks() {
        return !pendingTasks.isEmpty();
    }


    public void startReminderTask(long delayMillis) {
        if (future == null || future.isCancelled()) {
            if (hasPendingTasks()) {  Runnable task = pendingTasks.poll();
                if (task != null) {
                    future = scheduler.schedule(task, new Date(System.currentTimeMillis() + delayMillis).toInstant());
                }
            }
        }
    }

    public void stopReminderTask() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }

}
