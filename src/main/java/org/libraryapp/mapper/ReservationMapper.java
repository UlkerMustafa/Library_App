package org.libraryapp.mapper;

import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dao.entity.ReservationEntity;
import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dto.ReservationRequestDto;
import org.libraryapp.dto.ReservationResponseDto;
import org.libraryapp.util.enums.ReservationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationMapper {

    public ReservationEntity mapToEntity(ReservationRequestDto dto,UserEntity user, BookEntity book) {
        return ReservationEntity.builder()
                .user(user)
                .book(book)
                .reservationDate(LocalDate.now())
                .dueDate(dto.getDueDate())
                .status(ReservationStatus.PENDING)
                .active(true)
                .build();


    }
    public ReservationResponseDto mapToResponseDto(ReservationEntity reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getID())
                .bookId(reservation.getBook().getId())
                .userFullName(fullName(reservation))
                .bookTitle(reservation.getBook().getTitle())       // varsa
                .reservationDate(reservation.getReservationDate())
                .dueDate(reservation.getDueDate())
                .status(reservation.getStatus().name())
                .active(reservation.isActive())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
    public List<ReservationResponseDto> entityToDtoList(List<ReservationEntity> entities){
        var dtos =new ArrayList<ReservationResponseDto>();
        for (ReservationEntity e:entities){
            dtos.add(ReservationResponseDto.builder()
                    .id(e.getId())
                    .userId(e.getUser().getID())
                    .bookId(e.getBook().getId())
                    .userFullName(fullName(e))
                    .bookTitle(e.getBook().getTitle())
                    .reservationDate(e.getReservationDate())
                    .dueDate(e.getDueDate())
                    .status(e.getStatus().name())
                    .active(e.isActive())
                    .createdAt(e.getCreatedAt())
                    .build());
        }
        return dtos;

    }



    private String fullName(ReservationEntity e){
        return e.getUser().getName() + " " + e.getUser().getSurname();
}


}
