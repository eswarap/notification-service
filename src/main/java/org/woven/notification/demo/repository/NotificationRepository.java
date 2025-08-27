package org.woven.notification.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.notification.demo.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
