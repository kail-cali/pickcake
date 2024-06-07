package co.pickcake.image.controller;

import co.pickcake.image.dto.ImageSaveRequest;
import co.pickcake.image.dto.ImageSaveResponse;
import co.pickcake.image.service.ImageServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CakeImageStoreApi {

    private final ImageServer imageServer;

    @PostMapping("/api/image/store")
    public ResponseEntity<ImageSaveResponse> upload(
            @RequestPart(value = "image") MultipartFile image,
            @RequestParam(value = "storeName") String storeName,
            @RequestParam(value = "ext", defaultValue = "png") String ext) {
        ImageSaveRequest imageSaveRequest = new ImageSaveRequest(image, storeName, ext);

        ImageSaveResponse imageSaveResponse = null;

        try {
            imageSaveResponse = imageServer.uploadImage(imageSaveRequest);
        } catch (IOException e) {
            return (ResponseEntity<ImageSaveResponse>) ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(imageSaveResponse);
    }



}
