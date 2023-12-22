package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.PhotoDto;
import com.annak.handcrafted.mapper.PhotoMapper;
import com.annak.handcrafted.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    @Override
    public List<PhotoDto> getAll() {
        return photoRepository.findAll()
                .stream()
                .map(photoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PhotoDto> getById(Long id) {
        return photoRepository.findById(id)
                .map(photoMapper::toDTO);
    }
}
