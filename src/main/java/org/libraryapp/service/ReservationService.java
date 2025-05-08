package org.libraryapp.service;

import org.libraryapp.dto.ReservationRequestDto;
import org.libraryapp.dto.ReservationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    // Yeni rezervasiya yaratmaq
    ReservationResponseDto createReservation(ReservationRequestDto dto, String authHeader);

    // Bütün rezervasiyaları əldə etmək
    List<ReservationResponseDto> getAllReservations(String authHeader);



    // Kitabı geri qaytarmaq
    ResponseEntity<String> returnBook(Long id,String authHeader);
}
