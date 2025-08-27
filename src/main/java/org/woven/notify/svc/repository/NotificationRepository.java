package org.woven.notify.svc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.notify.svc.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
