package org.libraryapp.service;

import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dto.NotificationDto;
import org.libraryapp.util.enums.NotificationType;

public interface NotificationService {
    // 	Əsas ümumi istifadə olunan bildiriş göndərmə (DTO ilə)
    void sendNotification(NotificationDto dto);

    // Qeydiyyat zamanı bildiriş göndərmək
    void notifyUserRegistered(UserEntity user);


//    Kitab əlavə olunanda bildiriş göndərmək
//    void notifyBookAdded(UserEntity user, BookEntity book);


//    Kitab silinərkən bildiriş
//    void notifyBookDeleted(UserEntity user, BookEntity book);


//    Kitab gecikməsinə görə xəbərdarlıq
//    void notifyBookOverdue(UserEntity user, BookEntity book);

    //	İlk girişdə xoş gəldin mesajı
    void notifyWelcome(UserEntity user);
}
