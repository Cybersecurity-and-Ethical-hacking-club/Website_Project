package esc.first.demo.Blog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import esc.first.demo.Image.Image;
import esc.first.demo.imageV2.ImageV2;
import jakarta.persistence.*;

import java.awt.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_table")
public class Blog {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String content;
    @OneToMany(mappedBy = "blog",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ImageV2> imageV2s;
    //@OneToMany(mappedBy = "blog",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    //@JsonManagedReference
    //private List<Image> images;
    //public List<Image> getImages() {
    //    return images;
    //}

    public List<ImageV2> getImageV2s() {
        return imageV2s;
    }

    public void setImageV2s(List<ImageV2> imageV2s) {
        this.imageV2s = imageV2s;
    }

    public List<String> getFileNames(){
        ArrayList<String> list=new ArrayList<>();
        for(var img:imageV2s){
            list.add(img.getFilename());
        }
        return list;
    }

    //public void setImages(List<Image> images) {
        //this.images = images;
    //}



    //once the member class is created
    //private String author;
    private String category;
    //private List<Blob> images;

    public Blog() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public List<Image> getImages() {
//        return images;
//    }

//    public void setImages(List<Image> images) {
//        this.images = images;
//    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public Blog(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
