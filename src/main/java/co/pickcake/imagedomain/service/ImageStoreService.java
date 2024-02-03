package co.pickcake.imagedomain.service;



import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.imagedomain.dto.ImageSaveResponse;
import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.policies.policy.policy.FileNamePolicy;
import co.pickcake.imagedomain.repository.CakeImageRepository;
import co.pickcake.imagedomain.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageStoreService { // -> service 계층 ??

    @Value("${file.dir}")
    private String fileDir;

    private final FileNamePolicy fileNamePolicy;
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
        ImageFile imageFile = ImageFile.createImageFile(originalFilename, fileNamePolicy);
        imageFileRepository.save(imageFile);

        /* TODO 미리 아이디와 패스를 생성하고 실제 저장은  image server 에서 할 수 있도록 변경*/
//        transferImageServer(multipartFile, imageFile); //  -> ImageStoreApi.upload api 호출
        ImageSaveResponse imageSaveResponse = callImageServerStoreApi(multipartFile, imageFile.getStoreFileName());

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

    private ImageSaveResponse callImageServerStoreApi(MultipartFile multipartFile, String storeName) {
//        WebClient webClient = WebClient.builder().baseUrl(apiGatewayConfig.getImageServer()).build();
        WebClient webClient = WebClient.create(apiGatewayConfig.getImageServer());
        log.info("welcliet = {}", webClient);
        log.info("call api url = {}", apiGatewayConfig.getImageServer() + apiGatewayConfig.getUploadApi());

        Map<String, Object> bodyMap = new HashMap<>();

        bodyMap.put("image", multipartFile);
        bodyMap.put("storeName", storeName);

        ImageSaveResponse imageSaveResponse = webClient
                .post()
                .uri(apiGatewayConfig.getUploadApi())
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(ImageSaveResponse.class)
                .block();
//                .block(Duration.ofMillis(10));

        return imageSaveResponse;
    }

    private void transferImageServer(MultipartFile multipartFile, ImageFile imageFile) throws IOException {
        multipartFile.transferTo(new File(getFullPath(imageFile.getStoreFileName())));
    }

}
