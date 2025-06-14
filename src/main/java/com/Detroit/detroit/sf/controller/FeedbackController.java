package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.library.FileUpload;
import com.Detroit.detroit.sf.entity.Feedback;
import com.Detroit.detroit.sf.service.FeedbackService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")

public class FeedbackController {

    private final FeedbackService service;
    private final FileUpload fileUpload;

    public FeedbackController(FeedbackService service, FileUpload fileUpload) {
        this.service = service;
        this.fileUpload = fileUpload;

    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAll() {
        return ResponseEntity.ok(service.getAllFeedback());
    }

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback){
        return ResponseEntity.ok(service.addFeedback(feedback));
    }
    @PostMapping(path = "/file/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadFile(@RequestParam("document") MultipartFile file) throws IOException {
        String document = fileUpload.uploadFile(file);
        return ResponseEntity.ok(document);
    }


}
