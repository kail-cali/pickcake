package co.pickcake.imagedomain.service;


import co.pickcake.imagedomain.dto.ImageSaveRequest;
import co.pickcake.imagedomain.dto.ImageSaveResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServer {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public ImageSaveResponse uploadImage(ImageSaveRequest imageSaveRequest) throws IOException {
        MultipartFile images = imageSaveRequest.getImages();
        String storeName = imageSaveRequest.getStoreName();
        String ext = imageSaveRequest.getExt();
        ImageSaveResponse imageSaveResponse = new ImageSaveResponse(storeName, ext, HttpStatus.PROCESSING);

        images.transferTo(new File(getFullPath(storeName)));

        /* TODO 비동기로 이미저 서버에서 파일 저장 시 응답 다르게 저장해서 보낼 것*/
        imageSaveResponse.setStatus(HttpStatus.OK);

        return imageSaveResponse;
    }





}
