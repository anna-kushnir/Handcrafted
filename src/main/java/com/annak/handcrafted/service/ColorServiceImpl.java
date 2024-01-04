package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorDto> getAll() {
        return colorRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Map<String, Long> getColorStatistics() {
        List<Object[]> statisticsList = colorRepository.getColorStatistics();
        Map<String, Long> statisticsMap = new LinkedHashMap<>();

        for (Object[] row : statisticsList) {
            statisticsMap.put((String)row[0], (Long)row[1]);
        }
        return statisticsMap;
    }
}
