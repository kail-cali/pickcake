package co.pickcake.imagedomain.service;



import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.config.FileSystemConfig;
import co.pickcake.imagedomain.dto.ImageSaveRequest;
import co.pickcake.imagedomain.dto.ImageSaveResponse;
import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.imagedomain.repository.CakeImageRepository;
import co.pickcake.imagedomain.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageStoreService { // -> service 계층 ??

    @Value("${file.dir}")
    private String fileDir;
    private final FileSystemConfig fileSystemConfig;
    private final CakeImageRepository cakeImageRepository; /* NEEDEDTOFIX  Cake 생성 시 미리 만들기*/
    private final ImageFileRepository imageFileRepository;
    private final ApiGatewayConfig apiGatewayConfig;
    @Transactional
    public void save(CakeImages cakeImages) {
        cakeImageRepository.save(cakeImages);

    }
    @Transactional
    public void save(ImageFile imageFile) {
        imageFileRepository.save(imageFile);
    }

    public ImageFile findById(Long id) {
        return imageFileRepository.findById(id);
    }

    public CakeImages findByIdCake(Long id) {
        return cakeImageRepository.findById(id);
    }


    @Transactional
    public List<Long> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Long> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }


    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Transactional
    public Long storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        ImageFile imageFile = ImageFile.createImageFile(originalFilename, fileSystemConfig.fileNamePolicy());
        imageFileRepository.save(imageFile);

        /* TODO 미리 아이디와 패스를 생성하고 실제 저장은  image server 에서 할 수 있도록 변경*/
//        transferImageServer(multipartFile, imageFile); //  -> ImageStoreApi.upload api 호출
        ImageSaveResponse imageSaveResponse = upload(multipartFile, imageFile.getStoreFileName());

        return imageFile.getId();
    }

    @Transactional
    public void linkProfileFile(Long cakeImageId, Long imageFileId) throws IOException {
        ImageFile imageFile = imageFileRepository.findById(imageFileId);
        CakeImages cakeImages = cakeImageRepository.findById(cakeImageId);
        imageFile.setProfileCakeImages(cakeImages);
    }

    @Transactional
    public void linkImageFiles(Long cakeImageId, Long imageFileId) throws IOException {
        ImageFile imageFile = imageFileRepository.findById(imageFileId);
        CakeImages cakeImages = cakeImageRepository.findById(cakeImageId);
        imageFile.setProfileCakeImages(cakeImages);
    }

    public ImageSaveResponse upload(MultipartFile multipartFile, String storeName) {
        MultiValueMap<String, HttpEntity<?>> parts = uploadApiCreate(multipartFile, storeName);

        return uploadRequest(parts);
    }

    private static MultiValueMap<String, HttpEntity<?>> uploadApiCreate(MultipartFile multipartFile, String storeName) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", multipartFile.getResource());
        builder.part("storeName", storeName);
        builder.part("ext", storeName.substring(storeName.lastIndexOf(".")+1));
        MultiValueMap <String, HttpEntity<?>> parts = builder.build();
        return parts;
    }


    public static MultiValueMap<String, HttpEntity<?>> uploadApiCreate(ImageSaveRequest imageSaveRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imageSaveRequest.getImages().getResource());
        builder.part("storeName", imageSaveRequest.getStoreName());
        builder.part("ext", imageSaveRequest.getExt());
        return builder.build();
    }

    public ImageSaveResponse uploadRequest(MultiValueMap<String, HttpEntity<?>> parts) {
        WebClient uploadApi = WebClient.builder()
                                        .baseUrl(apiGatewayConfig.getImageServerGateWay())
                                        .build();

        /*TODO non-block or async & 예외처리 추가 */
        return uploadApi.post()
                .uri(apiGatewayConfig.getUploadApi())
                .bodyValue(parts)
                .retrieve()
                .bodyToMono(ImageSaveResponse.class)
                .block();
    }


    private void transferImageServer(MultipartFile multipartFile, ImageFile imageFile) throws IOException {
        multipartFile.transferTo(new File(getFullPath(imageFile.getStoreFileName())));
    }

}
