package com.bookstore.repositories;

import com.bookstore.models.StationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationTypeRepository extends JpaRepository<StationType, Long> {
}
