package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ColorDto;

import java.util.List;
import java.util.Map;

public interface ColorService {

    List<ColorDto> getAll();

    Map<String, Long> getColorStatistics();
}
