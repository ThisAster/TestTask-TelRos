package com.example.test_telros.photo.controller;


import com.example.test_telros.photo.service.PhotoStorageService;
import com.example.test_telros.user.entity.User;
import com.example.test_telros.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/users/{username}/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final UserService userService;
    private final PhotoStorageService photoStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@PathVariable String username,
                                              @RequestParam("file") MultipartFile file) {
        User user = userService.getUserByLogin(username);

        String fileName = photoStorageService.uploadPhoto(user.getUsername(), file);

        user.setPhotoKey(fileName);
        userService.saveUser(user);

        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/photo-url")
    public ResponseEntity<String> getPhotoUrl(@PathVariable String username) {
        User user = userService.getUserByLogin(username);

        if (user.getPhotoKey() == null) {
            return ResponseEntity.notFound().build();
        }

        String url = photoStorageService.getPublicUrl(user.getPhotoKey());
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePhoto(@PathVariable String username) {
        User user = userService.getUserByLogin(username);

        String fileName = user.getPhotoKey();
        if (fileName != null) {
            photoStorageService.deletePhoto(fileName);
            user.setPhotoKey(null);
            userService.saveUser(user);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadPhoto(@PathVariable String username) {
        User user = userService.getUserByLogin(username);
        if (user.getPhotoKey() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            InputStream stream = photoStorageService.downloadPhoto(user.getPhotoKey());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getPhotoKey() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
