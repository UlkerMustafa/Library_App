package org.libraryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dao.entity.ReservationEntity;
import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dao.repository.BookRepository;
import org.libraryapp.dao.repository.ReservationRepository;
import org.libraryapp.dao.repository.UserRepository;
import org.libraryapp.dto.ReservationRequestDto;
import org.libraryapp.dto.ReservationResponseDto;
import org.libraryapp.exception.BookAlreadyReturnedException;
import org.libraryapp.mapper.ReservationMapper;
import org.libraryapp.service.ReservationService;
import org.libraryapp.util.enums.ReservationStatus;
import org.libraryapp.util.helper.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationMapper reservationMapper;
    private final JwtService jwtService;
    private final EmailSender emailSender;

    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto dto, String authHeader) {
        // tokeni parse edib useri mueyyenlesdiriik
        var userId = jwtService.validateToken(authHeader).getBody();

        if (userId == null) {
            throw new RuntimeException("ID NULL");
        }

        // İstifadəçi və kitabı id-yə görə tap
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BookEntity book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Mapper vasitəsilə ReservationEntity-yə çeviririk
        ReservationEntity reservation = reservationMapper.mapToEntity(dto, user, book);
        reservation.setCreatedAt(LocalDateTime.now());  // Tarixi əlavə et

        // Reservation-ı saxlamaq
        ReservationEntity saved = reservationRepository.save(reservation);

        //  Email göndəririk
        String message = String.format("Hörmətli %s, '%s' adlı kitab üçün rezervasiya uğurla tamamlandı.",
                user.getName(), book.getTitle());

        emailSender.sendEmail(user.getEmail(), "Library_App - Rezervasiya Təsdiqi", message);


        // DTO-yə çevirib geri qaytarırıq
        return reservationMapper.mapToResponseDto(saved);
    }

    @Override
    public List<ReservationResponseDto> getAllReservations(String authHeader) {
        var userId = jwtService.validateToken(authHeader).getBody();

        if (userId == null) {
            throw new RuntimeException("Id null");
        }
        // Bütün rezervasiyaları tapırıq
        List<ReservationEntity> reservations = reservationRepository.findAllByUserID(userId);
        // PENDING statuslu rezervasiyaları süzürük
        List<ReservationEntity> pendingReservations = reservations.stream()
                .filter(reservation -> reservation.getStatus().equals(ReservationStatus.PENDING))
                .collect(Collectors.toList());

        // Hər birini DTO-ya çeviririk
            return reservationMapper.entityToDtoList(pendingReservations);
    }



    @Override
    public ResponseEntity<String> returnBook(Long id, String authHeader) {
        var userId = jwtService.validateToken(authHeader).getBody();

        if (userId == null) {
            throw new RuntimeException("ID NULL");
        }
        // İstifadəçi və kitabı tapıb, rezervasiyanı tapırıq
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        //geri qayatrmaq istenilen kitab qaytaran sexsindirmir?
        if (!reservation.getUser().getID().equals(userId)) {
            throw new RuntimeException("bu emeliyyati yerine yetirmeye icazeniz yoxdur");
        }

        if (reservation.getStatus().equals(ReservationStatus.RETURNED)) {
            throw new BookAlreadyReturnedException("bu kitab artiq qaytarilib.");
        }

        // Statusunu `RETURNED` olaraq təyin edirik
        reservation.setStatus(ReservationStatus.RETURNED);
        reservation.setUpdatedAt(LocalDateTime.now());

        // Dəyişiklikləri saxlayırıq
        reservationRepository.save(reservation);
        // Email göndəririk
        BookEntity book = reservation.getBook();
        String message = String.format("Hörmətli %s, '%s' adlı kitabı uğurla geri qaytardınız.",
                user.getName(), book.getTitle());

        emailSender.sendEmail(user.getEmail(), "Library_App - Kitab Qaytarıldı", message);


        return ResponseEntity.ok("Kitab geri qaytarildi.");
    }
}
