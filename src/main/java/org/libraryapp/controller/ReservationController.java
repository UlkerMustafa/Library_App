package org.libraryapp.controller;

import lombok.RequiredArgsConstructor;
import org.libraryapp.dto.ReservationRequestDto;
import org.libraryapp.dto.ReservationResponseDto;
import org.libraryapp.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Yeni rezervasiya yaratmaq
    @PostMapping("/create-reservation")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto dto,
                                                                    @RequestHeader("Authorization") String authHeader) {
        ReservationResponseDto response = reservationService.createReservation(dto,authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Bütün rezervasiyaları əldə etmək
    @GetMapping("/get-all-reservations")
    public List<ReservationResponseDto> getAllReservations(
            @RequestHeader("Authorization") String authHeader) {

        return reservationService.getAllReservations(authHeader);
    }



    // Kitabı geri qaytarmaq
    @PutMapping("/return-book/{id}")
    public ResponseEntity<String> returnBook(@PathVariable Long id,
                                             @RequestHeader("Authorization") String authHeader) {
       return reservationService.returnBook(id,authHeader);

    }
}
