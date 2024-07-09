package esc.first.demo.Document.Model;

import esc.first.demo.GroupProject.Models.GPModel;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String fileType;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "group_project_id")
    private GPModel groupProject;

    public Document() {}

    public Document(String filename, String fileType, byte[] data) {
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

    public void setFilename(String filename) {
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
        Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setGroupProject(GPModel groupProject){
        this.groupProject = groupProject;
    }

    public GPModel getGroupProject(){
        return this.groupProject;
    }

}
