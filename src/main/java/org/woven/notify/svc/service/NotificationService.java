package org.woven.notify.svc.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.woven.notify.svc.entity.Notification;
import org.woven.notify.svc.repository.NotificationRepository;

import java.util.List;

@Service
@Log
public class NotificationService {
    private final NotificationRepository repository;

    public NotificationService(final NotificationRepository repository) {
        this.repository = repository;
    }

    public Notification createNotification(String message) {
        return repository.save(new Notification(message));
    }

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public void markAsSent(Long id) {
        repository.findById(id).ifPresent(n -> {
            n.setSent(true);
            repository.save(n);
        });
    }
}
