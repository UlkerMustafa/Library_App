package org.libraryapp.dao.repository;


import org.libraryapp.dao.entity.ReservationEntity;
import org.libraryapp.util.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {

    List<ReservationEntity> findAllByUserID(Long ID);

}
