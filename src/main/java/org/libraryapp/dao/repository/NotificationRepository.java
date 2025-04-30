package org.libraryapp.dao.repository;

import org.libraryapp.dao.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
}
