package com.annak.handcrafted.controller.api;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/api/v1/photos")
@RestController
@RequiredArgsConstructor
public class PhotoRestController {

    private final ProductService productService;

    @ResponseBody
    @GetMapping(value = "/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable Long productId, HttpServletResponse httpServletResponse) throws IOException {
        Optional<ProductDto> productDtoOptional = productService.getById(productId);

        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            byte[] img = productDto.getPhoto();
            httpServletResponse.setContentType("image/jpeg");
            httpServletResponse.setContentLength(img.length);
            httpServletResponse.getOutputStream().write(img);
            return img;
        }
        else {
            return null;
        }
    }
}
