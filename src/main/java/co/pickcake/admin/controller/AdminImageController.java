package co.pickcake.admin.controller;



import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Slf4j
@Controller
@RequestMapping("/admin/images")
@PropertySource("classpath:/secure.properties")
public class AdminImageController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String uploadImageForm() {
        return "file/upload-form";
    }

    @PostMapping("/upload")
    public String saveImages(
            @RequestParam(name="itemName") String itemName,
            @RequestParam(name="file") MultipartFile file,
            HttpServletRequest request
            ) throws IOException {

        if (!file.isEmpty()) {
            String filePath = fileDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));
        }
        return "file/upload-form";
    }
}
