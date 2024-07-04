package esc.first.demo.Blog;

import esc.first.demo.Image.Image;
import esc.first.demo.Image.ImageResponseDto;

import java.util.List;

public record BlogResponseDto(
        String title,
        String category,
        List<String> fileName
) {
}
