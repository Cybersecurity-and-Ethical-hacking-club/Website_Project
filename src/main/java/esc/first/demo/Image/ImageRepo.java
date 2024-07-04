package esc.first.demo.Image;

import esc.first.demo.Blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image,Integer> {
}
