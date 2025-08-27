package org.woven.notification.demo.controller;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woven.notification.demo.entity.Notification;
import org.woven.notification.demo.service.NotificationService;
import org.woven.notification.demo.service.SchedulerService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Log
public class NotificationController {
    private final NotificationService notificationService;
    private final SchedulerService schedulerService;

    public NotificationController(final NotificationService notificationService,
                                  final SchedulerService schedulerService) {
        this.notificationService = notificationService;
        this.schedulerService = schedulerService;
    }

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody String message) {
        Notification notification = notificationService.createNotification(message);

        schedulerService.startReminderTask(() -> {
            log.info("Sending reminder: " + notification.getMessage());
            notificationService.markAsSent(notification.getId());
        }, 5000); // Delay of 5 seconds

        return ResponseEntity.ok("Notification scheduled");
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok().body(notificationService.getAllNotifications());
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopTask() {
        schedulerService.stopReminderTask();
        return ResponseEntity.ok("Reminder task stopped") ;
    }
}
