package com.annak.handcrafted.controller.api;

import com.annak.handcrafted.dto.PhotoDto;
import com.annak.handcrafted.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/api/v1/photos")
@RestController
@RequiredArgsConstructor
public class PhotoRestController {

    private PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Optional<PhotoDto> photoOptional = photoService.getById(id);

        if (photoOptional.isPresent()) {
            PhotoDto photoDto = photoOptional.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photoDto.data());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
