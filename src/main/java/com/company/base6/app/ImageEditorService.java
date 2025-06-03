package com.company.base6.app;

import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageEditorService {

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private DataManager dataManager;

    // Метод для изменения размера изображения
    public FileDescriptor resizeImage(FileRef originalDescriptor, int width, int height) throws IOException {
        // 1. Загрузить оригинальное изображение
        BufferedImage originalImage;
        try (InputStream is = fileStorage.openStream(originalDescriptor)) {
            originalImage = ImageIO.read(is);
        }

        // 2. Изменить размер
//        BufferedImage resizedImage = Thumbnails.of(originalImage)
//                .size(width, height)
//                .asBufferedImage();
//
//        // 3. Сохранить новое изображение
        FileDescriptor newDescriptor = new FileDescriptor();
//        newDescriptor.setName("resized_" + originalDescriptor.getName());
//        newDescriptor.setExtension(originalDescriptor.getExtension());
//
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(resizedImage, originalDescriptor.getExtension(), os);
//
//        try (InputStream is = new ByteArrayInputStream(os.toByteArray())) {
//            fileStorage.saveStream(newDescriptor, is);
//        }
//
//        // 4. Связать с товаром
//        Product product = dataManager.load(Product.class)
//                .query("select p from Product p where p.image.id = :id")
//                .parameter("id", originalDescriptor.getId())
//                .one();
//
//        product.setImage(newDescriptor);
//        dataManager.save(product);
//
        return dataManager.save(newDescriptor);
    }
}