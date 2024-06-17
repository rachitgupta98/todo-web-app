package org.plivo.scheduler;

import jakarta.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plivo.services.TaskService;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TaskRecurrenceScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void init() {
        log.info("Recurrence Scheduler Tasks initialized.");
        final long TEN_SECONDS = 10;
        Runtime.getRuntime().addShutdownHook(new BackgroundTasksShutdownHook(scheduler));
        scheduler.scheduleAtFixedRate(this::runScheduledTask, TEN_SECONDS, TEN_SECONDS, TimeUnit.SECONDS);
    }

    private void runScheduledTask() {
        var taskService = new TaskService();
        taskService.checkForRecurringTasks();
    }

    private static class BackgroundTasksShutdownHook extends Thread {
        private final ScheduledExecutorService scheduler;

        public BackgroundTasksShutdownHook(ScheduledExecutorService scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public void run() {
            log.info("Closing background tasks and services");
            scheduler.shutdownNow();
        }
    }
}
