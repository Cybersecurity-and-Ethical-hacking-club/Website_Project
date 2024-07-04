package esc.first.demo.Blog;

import esc.first.demo.Image.Image;
import esc.first.demo.Image.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
public record BlogDTO (
        String title,
        String content,
        String category,
        //List<ImageDTO> imageDTOS
        List<MultipartFile> file
) {

}
