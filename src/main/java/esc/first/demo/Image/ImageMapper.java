package esc.first.demo.Image;

import esc.first.demo.Blog.Blog;
import esc.first.demo.Blog.BlogDTO;
import esc.first.demo.Blog.BlogResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {
    public ImageResponseDto toImageResponseDto(Image image){
        return new ImageResponseDto(image.getFileName());
    }
    public Image toImage(ImageDTO dto){
        if(dto==null){
            throw new NullPointerException("the blog dto is null");
        }
        var image=new Image();
        image.setFileName(dto.fileName());
        image.setData(dto.data());
        return image;
    }
}
