package esc.first.demo.GroupProject.Services;


import esc.first.demo.Document.Model.Document;
import esc.first.demo.Document.Repositories.DocumentRepo;
import esc.first.demo.GroupProject.ExceptionNotFound.ResourceNotFoundException;
import esc.first.demo.GroupProject.Models.GPModel;
import esc.first.demo.GroupProject.Repositories.GPRepository;
import esc.first.demo.Model.User;
import esc.first.demo.Repositpries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GPServices {
    @Autowired
    private UserRepo memberRepository;

    @Autowired
    private DocumentRepo documentRepository;
    private final GPRepository gpRepos;
    @Autowired
    public GPServices(GPRepository gpRepo) {
        this.gpRepos = gpRepo;
    }

    public List<GPModel> test(){
        return gpRepos.findAll();
    }

    public GPModel createGroupProject(GPModel groupProject) {
        groupProject.setCreatedTimestamp(LocalDateTime.now());
        groupProject.setLastModifiedTimestamp(LocalDateTime.now());
        return gpRepos.save(groupProject);
    }

    public GPModel getGroupProjectById(Long id) throws Throwable {
        return gpRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupProject", "id", id));
    }


    public List<GPModel> getGroupProjectsByDocumentId(Long documentId) {
        // Assuming you have a method in DocumentRepository to find the document by its ID
        Document document = documentRepository.findById(documentId).orElse(null);
        if (document != null) {
            return List.of(document.getGroupProject());
        } else {
            return new ArrayList<>();
        }
    }


    public List<GPModel> getAllGroupProjects() {
        return gpRepos.findAll();
    }

    public void deleteGroupProject(Long id) throws Throwable {
        GPModel project = getGroupProjectById(id);
        gpRepos.delete(project);
    }

    public Optional<GPModel> searchProjectsByTag(String tag) {
        return gpRepos.findByTagsContaining(tag);
    }

    public Optional<GPModel> searchProjectsByState(String state) {
        return gpRepos.findByState(state);
    }

    public GPModel updateProjectState(Long projectId, String state) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        project.setState(state);
        project.setLastModifiedTimestamp(LocalDateTime.now());
        return gpRepos.save(project);
    }

    public GPModel updateProjectTags(Long projectId, List<String> tags) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        project.setTags(tags);
        project.setLastModifiedTimestamp(LocalDateTime.now());
        return gpRepos.save(project);
    }

    public GPModel updateGroupProject(Long id, Map<String, Object> updates) throws Throwable {
        GPModel existingProject = getGroupProjectById(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingProject.setName((String) value);
                    break;
                case "description":
                    existingProject.setDescription((String) value);
                    break;
                case "startDate":
                    existingProject.setStartDate(LocalDate.parse((String) value, DateTimeFormatter.ISO_DATE));
                    break;
                case "endDate":
                    existingProject.setEndDate(LocalDate.parse((String) value, DateTimeFormatter.ISO_DATE));
                    break;
                case "state":
                    existingProject.setState((String) value);
                    break;
                case "visibility":
                    existingProject.setVisibility((String) value);
                    break;
                case "tags":
                    existingProject.setTags((List<String>) value);
                    break;
                // Add more cases as needed
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        existingProject.setLastModifiedTimestamp(LocalDateTime.now());
        return gpRepos.save(existingProject);
    }


    public GPModel addMemberToProject(Long projectId, User member) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        member.setGroupProject(project);
        project.getMembers().add(member);
        memberRepository.save(member);
        return gpRepos.save(project);
    }

    public GPModel removeMemberFromProject(Long projectId, Long memberId) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        User member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Models.Member", "id", memberId));
        project.getMembers().remove(member);
        memberRepository.delete(member);
        return gpRepos.save(project);
    }

    // Add and remove documents
    public GPModel addDocumentToProject(Long projectId, Document document) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        document.setGroupProject(project);
        project.getDocuments().add(document);
        documentRepository.save(document);
        return gpRepos.save(project);
    }

    public GPModel removeDocumentFromProject(Long projectId, Long documentId) throws Throwable {
        GPModel project = getGroupProjectById(projectId);
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Models.Document", "id", documentId));
        project.getDocuments().remove(document);
        documentRepository.delete(document);
        return gpRepos.save(project);
    }

}
