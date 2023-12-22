package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.mapper.ColorMapper;
import com.annak.handcrafted.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public List<ColorDto> getAll() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::toDTO)
                .collect(Collectors.toList());
    }
}
