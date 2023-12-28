package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorDto> getAll() {
        return colorRepository.findAllByOrderByNameAsc();
    }
}
