package esc.first.demo.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepo extends JpaRepository<Blog,Integer> {
    List<Blog> findAllByTitleContaining(String Title);
}
