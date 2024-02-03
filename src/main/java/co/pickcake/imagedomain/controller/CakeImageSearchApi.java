package co.pickcake.imagedomain.controller;

import co.pickcake.imagedomain.service.ImageServer;
import co.pickcake.imagedomain.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CakeImageSearchApi {

    private final ImageServer imageServer;

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable(name="filename") String filename) throws MalformedURLException {
        return new UrlResource("file:"+ imageServer.getFullPath(filename));
    }


}
