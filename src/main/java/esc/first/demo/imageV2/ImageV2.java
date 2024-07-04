package esc.first.demo.imageV2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import esc.first.demo.Blog.Blog;
import jakarta.persistence.*;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Objects;
@Entity
public class ImageV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String fileType;
    @Lob
    private byte[] data;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public ImageV2() {}

    public ImageV2(String filename, String fileType, byte[] data) {
        this.filename = filename;
        this.fileType = fileType;
        this.data = data;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        ImageV2 document = (ImageV2) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}


