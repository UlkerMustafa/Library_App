package org.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestDto {

    private Long bookId;  // rezerv edilən kitabın ID-si
//    private LocalDate reservationDate;
    private LocalDate dueDate;

}
