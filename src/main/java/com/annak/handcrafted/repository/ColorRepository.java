package com.annak.handcrafted.repository;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<ColorDto> findAllByOrderByNameAsc();
}
