package esc.first.demo.Blog;

import esc.first.demo.Image.ImageDTO;
import esc.first.demo.Image.ImageMapper;
import esc.first.demo.Image.ImageRepo;
import esc.first.demo.Image.ImageResponseDto;
import esc.first.demo.imageV2.ImageRepoV2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private final BlogMapper blogMapper;
    private final BlogRepo repo;
    private final ImageRepo imageRepo;
    private final ImageMapper imageMapper;

    public BlogService(BlogMapper blogMapper, BlogRepo repo,ImageRepo imageRepo,ImageMapper imageMapper) {
        this.blogMapper = blogMapper;
        this.repo = repo;
        this.imageRepo=imageRepo;
        this.imageMapper=imageMapper;
    }
    public BlogResponseDto saveBlog(@ModelAttribute BlogDTO dto) throws IOException {
        var blog=blogMapper.toBlog(dto);

        for(var img :dto.file()){
            ImageDTO imageDTO=new ImageDTO(img.getOriginalFilename(),img.getBytes());
            saveImage(imageDTO);
        }
        var savedBlog= repo.save(blog);
        return blogMapper.toBlogResponseDto(savedBlog);
    }
    public ImageResponseDto saveImage(ImageDTO dto) throws IOException {
        var image=imageMapper.toImage(dto);
        var savedImage=imageRepo.save(image);
        return imageMapper.toImageResponseDto(savedImage);
    }
    public List<BlogResponseDto> findALL(){

        return repo.findAll()
                .stream()
                .map(blogMapper::toBlogResponseDto)
                .collect(Collectors.toList());
    }
    public BlogResponseDto findBlogById(Integer id){
        return repo.findById(id)
                .map(blogMapper::toBlogResponseDto)
                .orElse(null);
    }
    public List<BlogResponseDto> findBlogByTitle(String title){
        return repo.findAllByTitleContaining(title)
                .stream()
                .map(blogMapper::toBlogResponseDto)
                .collect(Collectors.toList());
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }
}
