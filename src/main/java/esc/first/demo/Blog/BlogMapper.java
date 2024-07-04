package esc.first.demo.Blog;

import esc.first.demo.Image.Image;
import esc.first.demo.Image.ImageMapper;
import esc.first.demo.imageV2.ImageV2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogMapper {
    public BlogMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public  final ImageMapper imageMapper;
    public BlogResponseDto toBlogResponseDto(Blog blog){
        return new BlogResponseDto(blog.getTitle(),blog.getCategory(),blog.getFileNames());
    }
    public Blog toBlog(BlogDTO dto) throws IOException {

        if(dto==null){
            throw new NullPointerException("the blog dto is null");
        }
//        List<Image> images = new ArrayList<>();
//        for(var img :dto.imageDTOS()){
//            images.add(imageMapper.toImage(img));
//        }
        var blog=new Blog();
        ImageV2 image=new ImageV2();
        ArrayList<ImageV2> images=new ArrayList<>();
        for (var eachFile:dto.file()){
            image.setFileName(eachFile.getOriginalFilename());
            image.setFileType(eachFile.getContentType());
            image.setData(eachFile.getBytes());
            images.add(image);
            image=new ImageV2();
        }

        //image.setFileName(dto.file().getOriginalFilename());
        //image.setData(dto.file().getBytes());
        //images.add(image);
        blog.setTitle(dto.title());
        blog.setCategory(dto.category());
        blog.setContent(dto.content());
        blog.setImageV2s(images);
        return blog;
    }
}
