package org.libraryapp.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class NotificationDto {
    private Long id;
    private String message;
    private Boolean isReady;
    private LocalDateTime createdAt;
    private String notificationType;
    private String username;
}
