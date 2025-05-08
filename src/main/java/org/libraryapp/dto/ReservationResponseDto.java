package org.libraryapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {

    private Long id;
    private Long userId;
    private Long bookId;
    private String userFullName; // istəyə bağlı olaraq əlavə məlumat
    private String bookTitle;
    private LocalDate reservationDate;
    private LocalDate dueDate;
    private boolean active;
    private String status;
    private LocalDateTime createdAt;

}
