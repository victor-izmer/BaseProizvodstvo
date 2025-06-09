package com.company.base6.app;

import com.vaadin.flow.component.upload.Receiver;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorageException;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.UuidProvider;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.component.upload.receiver.TemporaryStorageFileData;
import io.jmix.flowui.upload.TemporaryStorage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class AdditionalFileStorageProcessor {

    private final TemporaryStorage temporaryStorage;
    private final FileStorageLocator fileStorageLocator;

    public AdditionalFileStorageProcessor(TemporaryStorage temporaryStorage, FileStorageLocator fileStorageLocator) {
        this.temporaryStorage = temporaryStorage;
        this.fileStorageLocator = fileStorageLocator;
    }

    public void uploadFile(String storageName, Receiver receiver, FileStorageUploadField source,
                           Supplier<String> pathSupplier) {
        if (receiver instanceof FileTemporaryStorageBuffer storageBuffer) {
            TemporaryStorageFileData fileData = storageBuffer.getFileData();

            UUID fileId = fileData.getFileInfo().getId();
            String fileName = fileData.getFileName();
            File file = temporaryStorage.getFile(fileId);

            if (file != null) {
                AdditionalLocalFileStorage storage = fileStorageLocator.getByName(storageName);
                String path = pathSupplier.get() + "/" + createUuidFilename(fileName);

                FileRef fileRef = new FileRef(storageName, path, fileName);

                try (InputStream io = new FileInputStream(file)) {
                    storage.saveStream(fileRef, io);
                } catch (FileNotFoundException e) {
                    throw new FileStorageException(FileStorageException.Type.FILE_NOT_FOUND,
                            "Temp file is not found " + file.getAbsolutePath());
                } catch (IOException e) {
                    throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION, fileName);
                } finally {
                    temporaryStorage.deleteFile(fileId);
                }

                source.setValue(fileRef);
            }
        }
    }

    private String createUuidFilename(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        if (StringUtils.isNotEmpty(extension)) {
            return UuidProvider.createUuid() + "." + extension;
        } else {
            return UuidProvider.createUuid().toString();
        }
    }
}
