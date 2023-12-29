package com.annak.handcrafted.mapper;

import com.annak.handcrafted.entity.Color;
import com.annak.handcrafted.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ColorInProductMapper {

    private final ColorRepository colorRepository;

    public String colorsToString(List<Color> colorList) {
        StringBuilder colors = new StringBuilder();
        if (colorList.isEmpty()) {
            return "";
        }
        for (Color color : colorList) {
            colors.append(color.getName()).append(", ");
        }
        return colors.delete(colors.length() - 2, colors.length()).toString();
    }

    public List<Color> colorsToList(String colors) {
        List<Color> colorList = new ArrayList<>();
        for (String colorName : colors.toLowerCase().split(", ")) {
            if (colorRepository.existsByName(colorName)) {
                Color color = colorRepository.findByName(colorName);
                colorList.add(color);
            }
            else {
                Color color = new Color();
                color.setName(colorName);
                colorList.add(colorRepository.save(color));
            }
        }
        return colorList;
    }
}
