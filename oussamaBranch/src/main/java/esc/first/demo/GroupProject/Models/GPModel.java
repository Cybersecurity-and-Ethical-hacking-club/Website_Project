package esc.first.demo.GroupProject.Models;

import esc.first.demo.Document.Model.Document;
import esc.first.demo.Model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class GPModel {
    @Id
    @SequenceGenerator(
            name = "gp_sequence",
            sequenceName = "gp_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gp_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String state; // "Not Started", "In Progress", "Completed"
    private LocalDateTime createdTimestamp;
    private LocalDateTime lastModifiedTimestamp;
    private String visibility; // e.g., "Public", "Private"

    @ManyToMany(mappedBy = "groupProjects", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "groupProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    public GPModel(Long id, String name, String description, LocalDateTime createdTimestamp, LocalDateTime lastModifiedTimestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public GPModel() {
    }

    public GPModel(Long id, String name, String description, LocalDate startDate, LocalDate endDate, String state, LocalDateTime createdTimestamp, LocalDateTime lastModifiedTimestamp, String visibility, List<User> members, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.createdTimestamp = createdTimestamp;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
        this.visibility = visibility;
        this.members = members;
        this.tags = tags;
    }

    public GPModel(Long id, String name, String description, LocalDate startDate, LocalDate endDate, String state, LocalDateTime createdTimestamp, LocalDateTime lastModifiedTimestamp, String visibility, List<User> members, List<Document> documents, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.createdTimestamp = createdTimestamp;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
        this.visibility = visibility;
        this.members = members;
        this.documents = documents;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(LocalDateTime lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GPModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", lastModifiedTimestamp=" + lastModifiedTimestamp +
                ", visibility='" + visibility + '\'' +
                ", members=" + members +
                ", documents=" + documents +
                ", tags=" + tags +
                '}';
    }
}
