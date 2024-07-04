package esc.first.demo.imageV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageRepoV2 imageRepo;

    @GetMapping
    public List<ImageV2> getAllDocuments() {
        return imageRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageV2> getDocumentById(@PathVariable Long id) {
        Optional<ImageV2> document = imageRepo.findById(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ImageV2> updateDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return (ResponseEntity<ImageV2>) imageRepo.findById(id)
                .map(document -> {
                    try {
                        document.setFileName(file.getOriginalFilename());
                        document.setFileType(file.getContentType());
                        document.setData(file.getBytes());
                        imageRepo.save(document);
                        return ResponseEntity.ok(document);
                    } catch (IOException e) {
                        return ResponseEntity.status(500).build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable Long id) {
        return imageRepo.findById(id)
                .map(document -> {
                    imageRepo.delete(document);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        return imageRepo.findById(id)
                .map(document -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(document.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFilename() + "\"")
                        .body(document.getData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageV2> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            ImageV2 image = new ImageV2(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageRepo.save(image);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}


