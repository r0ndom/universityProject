package net.github.rtc.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Value("${img.save.folder}")
    private String imageFolder;

    @RequestMapping(value = "/{image:.+}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImage(@PathVariable final String image) throws IOException {
       final  File file = new File(imageFolder + image);
        return Files.readAllBytes(file.toPath());
    }
}
