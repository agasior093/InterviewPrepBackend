package pl.agasior.interviewprep.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.agasior.interviewprep.dto.ImageDto;
import pl.agasior.interviewprep.services.image.ImageUploader;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
class ImageController {
    private final ImageUploader imageUploader;

    @PostMapping("/upload")
    ResponseEntity<ImageDto> uploadQuestionImage(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageUploader.uploadImage(file));
    }
}