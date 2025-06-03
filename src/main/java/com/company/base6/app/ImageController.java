package com.company.base6.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageEditorService imageEditorService;

    @PostMapping("/resize/{productId}")
    public ResponseEntity<String> resizeProductImage(
//            @PathVariable UUID productId,
//            @RequestParam int width,
//            @RequestParam int height
    ) {
//        Product product = dataManager.load(Product.class).id(productId).one();
//        FileDescriptor originalImage = product.getImage();
//
        //            FileDescriptor newImage = imageEditorService.resizeImage(originalImage, width, height);
        FileDescriptor newImage = new FileDescriptor();
//            return ResponseEntity.ok("Изображение обновлено: " + newImage.getName());
        return ResponseEntity.ok("Изображение обновлено: ");
    }
    }
