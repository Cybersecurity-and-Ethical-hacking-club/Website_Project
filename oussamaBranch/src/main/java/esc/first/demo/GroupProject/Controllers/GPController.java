package esc.first.demo.GroupProject.Controllers;


import esc.first.demo.GroupProject.Models.GPModel;
import esc.first.demo.GroupProject.Services.GPServices;
import esc.first.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/gp")
public class GPController {
    GPServices gpServices;
    @Autowired
    public GPController(GPServices gpServices) {
        this.gpServices = gpServices;
    }


    @PostMapping
    public ResponseEntity<GPModel> createGroupProject(@RequestBody GPModel groupProject) {
        GPModel savedProject = gpServices.createGroupProject(groupProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GPModel> getGroupProjectById(@PathVariable Long id) throws Throwable {
        GPModel project = gpServices.getGroupProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<GPModel>> getAllGroupProjects() {
        List<GPModel> projects = gpServices.getAllGroupProjects();
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupProject(@PathVariable("id") Long id) throws Throwable {
        gpServices.deleteGroupProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/tag")
    public ResponseEntity<Optional<GPModel>> searchProjectsByTag(@RequestParam String tag) {
        Optional<GPModel> projects = gpServices.searchProjectsByTag(tag);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/search/state")
    public ResponseEntity<Optional<GPModel>> searchProjectsByState(@RequestParam String state) {
        Optional<GPModel> projects = gpServices.searchProjectsByState(state);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{documentId}/group-projects")
    public ResponseEntity<List<GPModel>> getGroupProjectsByDocumentId(@PathVariable Long documentId) {
        List<GPModel> groupProjects = gpServices.getGroupProjectsByDocumentId(documentId);
        if (!groupProjects.isEmpty()) {
            return ResponseEntity.ok(groupProjects);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @PatchMapping("/{projectId}/state")
    public ResponseEntity<GPModel> updateProjectState(@PathVariable Long projectId, @RequestParam String state) throws Throwable {
        GPModel updatedProject = gpServices.updateProjectState(projectId, state);
        return ResponseEntity.ok(updatedProject);
    }

    @PatchMapping("/{projectId}/tags")
    public ResponseEntity<GPModel> updateProjectTags(@PathVariable Long projectId, @RequestParam List<String> tags) throws Throwable {
        GPModel updatedProject = gpServices.updateProjectTags(projectId, tags);
        return ResponseEntity.ok(updatedProject);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<GPModel> updateGroupProject(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Throwable {
        GPModel updatedProject = gpServices.updateGroupProject(id, updates);
        return ResponseEntity.ok(updatedProject);
    }

    @PostMapping("/{projectId}/members")
    public ResponseEntity<GPModel> addMemberToProject(@PathVariable Long projectId, @RequestBody User member) throws Throwable {
        GPModel updatedProject = gpServices.addMemberToProject(projectId, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProject);
    }

    @DeleteMapping("/{projectId}/members/{memberId}")
    public ResponseEntity<GPModel> removeMemberFromProject(@PathVariable Long projectId, @PathVariable Long memberId) throws Throwable {
        GPModel updatedProject = gpServices.removeMemberFromProject(projectId, memberId);
        return ResponseEntity.ok(updatedProject);
    }

    @PostMapping("/{projectId}/documents")
    public <Document> ResponseEntity<GPModel> addDocumentToProject(@PathVariable Long projectId, @RequestBody Document document) throws Throwable {
        GPModel updatedProject = gpServices.addDocumentToProject(projectId, (esc.first.demo.Document.Model.Document) document);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProject);
    }

    @DeleteMapping("/{projectId}/documents/{documentId}")
    public ResponseEntity<GPModel> removeDocumentFromProject(@PathVariable Long projectId, @PathVariable Long documentId) throws Throwable {
        GPModel updatedProject = gpServices.removeDocumentFromProject(projectId, documentId);
        return ResponseEntity.ok(updatedProject);
    }

}
