package co.pickcake.imagedomain.controller;


import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.imagedomain.repository.CakeImageRepository;
import co.pickcake.imagedomain.repository.ImageFileRepository;
import co.pickcake.imagedomain.service.ImageStoreService;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;


import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CakeImageController {

    private final ImageStoreService imageStoreService;

    @GetMapping(
            value = "/temp",
            produces = IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] showImageApi(
            @RequestParam(name = "itemId") Long itemId) throws IOException {
        String storeFileName = imageStoreService.findById(itemId).getStoreFileName();

        InputStream in = getClass().getResourceAsStream(imageStoreService.getFullPath(storeFileName));
        return IOUtils.toByteArray(in);
    }


    @GetMapping("/admin/images/new")
    public String newImage(@ModelAttribute ImageForm form) {
        return "items/item-form";
    }

    @PostMapping("/admin/images/new")
    public String saveImages(@ModelAttribute ImageForm form, RedirectAttributes redirectAttributes) throws IOException {

        Long profileImageId = imageStoreService.storeFile(form.getProfileImage());
        List<Long> imageFileIds = imageStoreService.storeFiles(form.getImageFiles());

        CakeImages cakeImages = CakeImages.createCakeImages(form.getItemName());

        imageStoreService.save(cakeImages);

        imageStoreService.linkProfileFile(cakeImages.getId(), profileImageId);

        for (Long imageFileId : imageFileIds) {
            imageStoreService.linkImageFiles(cakeImages.getId(), imageFileId);
        }

        redirectAttributes.addAttribute("itemId", cakeImages.getId());
        redirectAttributes.addAttribute("profile", form.getProfileImage());
        redirectAttributes.addAttribute("details", form.getImageFiles());
        return "redirect:/admin/images/{itemId}";
    }

    @GetMapping("/admin/images/{id}")
    public String view(@PathVariable(name="id") Long id, Model model) {
        CakeImages cakeImages = imageStoreService.findByIdCake(id);
        model.addAttribute("item", cakeImages);
        return "items/item-view";
    }

//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource downloadImage(@PathVariable(name="filename") String filename) throws MalformedURLException {
//        return new UrlResource("file:"+ imageStoreService.getFullPath(filename));
//    }


    @ResponseBody
    @GetMapping("/images/{filename}/{caked}")
    public Resource showImage(@PathVariable Map<String, String> pathVarableMap) throws MalformedURLException {
        String filename = pathVarableMap.get("filename");
        String caked = pathVarableMap.get("caked");

        return new UrlResource("file:"+ imageStoreService.getFullPath(filename));
    }




}
