package org.libraryapp.dao.entity;


import jakarta.persistence.*;
import lombok.*;
import org.libraryapp.util.enums.NotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications ")
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

// Hər bildiriş bir user-ə aiddir
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String message;
    private boolean isReady;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


}
