package org.woven.notify.svc.controller;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.woven.notify.svc.entity.Notification;
import org.woven.notify.svc.service.NotificationService;
import org.woven.notify.svc.service.SchedulerService;

import java.util.List;

@RestController
@Log
public class NotificationController {
    private final NotificationService notificationService;
    private final SchedulerService schedulerService;

    public NotificationController(final NotificationService notificationService,
                                  final SchedulerService schedulerService) {
        this.notificationService = notificationService;
        this.schedulerService = schedulerService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody String message) {
        Notification notification = notificationService.createNotification(message);
        schedulerService.addTaskToQueue(()->{
            log.info("Sending notification: " + notification.getMessage());
            notificationService.markAsSent(notification.getId());
        });
        schedulerService.startReminderTask(5000); // Delay of 10 seconds
        return ResponseEntity.ok("Notification scheduled");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok().body(notificationService.getAllNotifications());
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopTask() {
        schedulerService.stopReminderTask();
        return ResponseEntity.ok("Reminder task stopped") ;
    }

    @GetMapping("/start")
    public ResponseEntity<String> startTask() {
        schedulerService.startReminderTask(5000);
        return ResponseEntity.ok("Reminder task started") ;
    }
}
