package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.PhotoDto;

import java.util.List;
import java.util.Optional;

public interface PhotoService {

    List<PhotoDto> getAll();

    Optional<PhotoDto> getById(Long id);
}
