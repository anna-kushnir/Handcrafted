package com.annak.handcrafted.repository;

import com.annak.handcrafted.dto.PhotoDto;
import com.annak.handcrafted.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<PhotoDto> findAllByOrderByIdAsc();
}
