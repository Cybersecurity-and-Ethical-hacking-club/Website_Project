package esc.first.demo.Blog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
public class BlogController {
    //private final BlogRepo repo;
    private final BlogService blogService;
    @PostMapping("/Createblog")
    public BlogResponseDto saveBlog(@Valid @ModelAttribute BlogDTO dto) throws IOException,URISyntaxException {
        return this.blogService.saveBlog(dto);
    }

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public List<BlogResponseDto> get(){
        return this.blogService.findALL();
    }
    @GetMapping("/blogs/{blog-id}")
    public BlogResponseDto findBlogById(@PathVariable("blog-id") Integer id){
        return this.blogService.findBlogById(id);
   }

    @GetMapping("/blogs/search/{blog-title}")
    public List<BlogResponseDto> findStudentById(@PathVariable("blog-title") String title){
        return this.blogService.findBlogByTitle(title);
   }
    @DeleteMapping("/blogs/{blog-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("blog-id") Integer id){
        this.blogService.delete(id);
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
    var errors=new HashMap<String ,String>();
    e.getBindingResult().getAllErrors()
            .forEach(error ->{
                var fieldname=((FieldError)error).getField();
                var errorMessage=error.getDefaultMessage();
                errors.put(fieldname,errorMessage);
            });
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
}
}
