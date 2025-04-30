package org.libraryapp.service.impl;


import lombok.RequiredArgsConstructor;
import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dao.entity.NotificationEntity;
import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dao.repository.NotificationRepository;
import org.libraryapp.dao.repository.UserRepository;
import org.libraryapp.dto.NotificationDto;
import org.libraryapp.service.NotificationService;
import org.libraryapp.util.NotificationMessageBuilder;
import org.libraryapp.util.enums.NotificationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    public void sendNotification(NotificationDto dto) {
        //istifadecini tapiriq
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUsername()));


        NotificationType type = NotificationType.valueOf(dto.getNotificationType());


      // NotificationEntity obyektini builder ilə qururuq
        NotificationEntity notification = NotificationEntity.builder()
                .user(user)
                .message(dto.getMessage())
                .isReady(dto.getIsReady() != null ? dto.getIsReady() : false)
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .notificationType(type)
                .build();
        notificationRepository.save(notification);

        //tipe esasen yonlendirme
        switch (type){
            case CONSOLE ->sendToConsole(user,dto.getMessage());
            case EMAIL -> sendToEmail(user,dto.getMessage());
            case LOG -> sendToLog(user,dto.getMessage());
        }


    }
    // Specific notification cases



    @Override
    public void notifyUserRegistered(UserEntity user) {
        String message= NotificationMessageBuilder.buildUserRegistrationMessage(user.getName());
        NotificationDto dto=buildDto(user,message,NotificationType.EMAIL);
        sendNotification(dto);

    }

//    @Override
//    public void notifyBookAdded(UserEntity user, BookEntity book) {
//        String message = NotificationMessageBuilder.buildBookAddedMessage(book.getTitle());
//        NotificationDto dto=buildDto(user,message,NotificationType.CONSOLE);
//        sendNotification(dto);
//
//    }

//    @Override
//    public void notifyBookDeleted(UserEntity user, BookEntity book) {
//        String message = NotificationMessageBuilder.buildBookDeletedMessage(book.getTitle());
//        NotificationDto dto = buildDto(user, message, NotificationType.LOG);
//        sendNotification(dto);
//
//    }

//    @Override
//    public void notifyBookOverdue(UserEntity user, BookEntity book) {
//        String message = NotificationMessageBuilder.buildBookOverdueReminder(book.getTitle());
//        NotificationDto dto = buildDto(user, message, NotificationType.EMAIL);
//        sendNotification(dto);
//
//    }

    @Override
    public void notifyWelcome(UserEntity user) {
        String message = NotificationMessageBuilder.buildWelcomeMessage(user.getName());
        NotificationDto dto = buildDto(user, message, NotificationType.CONSOLE);
        sendNotification(dto);

    }

    // Helper methods

    private NotificationDto buildDto(UserEntity user,String message,NotificationType type){
        return NotificationDto.builder()
                .username(user.getName())
                .message(message)
                .notificationType(type.name())
                .isReady(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
    private void sendToConsole(UserEntity user, String message) {
        System.out.println(" [CONSOLE] User: " + user.getName() + " | Message: " + message);
    }

    private void sendToEmail(UserEntity user, String message) {
        // Gerçək email sistemi əvəzlənə bilər
        System.out.println(" [EMAIL] To: " + user.getEmail() + " | Message: " + message);
    }

    private void sendToLog(UserEntity user, String message) {
        System.out.println(" [LOG] " + LocalDateTime.now() + " | User: " + user.getName() + " | Message: " + message);
    }
}
