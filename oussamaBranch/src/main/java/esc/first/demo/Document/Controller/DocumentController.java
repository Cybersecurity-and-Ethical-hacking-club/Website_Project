package esc.first.demo.Document.Controller;

import esc.first.demo.Document.Model.Document;
import esc.first.demo.Document.Repositories.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentRepo documentRepository;

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentRepository.findById(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return (ResponseEntity<Document>) documentRepository.findById(id)
                .map(document -> {
                    try {
                        document.setFilename(file.getOriginalFilename());
                        document.setFileType(file.getContentType());
                        document.setData(file.getBytes());
                        documentRepository.save(document);
                        return ResponseEntity.ok(document);
                    } catch (IOException e) {
                        return ResponseEntity.status(500).build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable Long id) {
        return documentRepository.findById(id)
                .map(document -> {
                    documentRepository.delete(document);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        return documentRepository.findById(id)
                .map(document -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(document.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFilename() + "\"")
                        .body(document.getData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            Document document = new Document(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            documentRepository.save(document);
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}