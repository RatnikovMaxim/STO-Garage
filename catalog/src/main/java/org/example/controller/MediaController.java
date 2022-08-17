package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.MediaUploadResponseDTO;
import org.example.manager.MediaManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class MediaController {
    private MediaManager manager;


    @RequestMapping("/media/upload-data")
    public MediaUploadResponseDTO uploadBytes(@RequestBody byte[] data) throws IOException {
        return manager.upload(data);

    }
}