package esc.first.demo.Image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import esc.first.demo.Blog.Blog;
import jakarta.persistence.*;

@Entity
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    @Lob
    private byte[] data;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public Image(){
    }

    public Image(Integer id, String fileName, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
    }
    @Override
    public String toString() {
        return "Image{" +
                "fileName='" + fileName + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

}
