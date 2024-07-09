package esc.first.demo.Model;

import esc.first.demo.GroupProject.Models.GPModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Getter
    private String username;
    private boolean enabled;
    private boolean isEmailConfirmed;
    private boolean isAdminConfirmed;
    @Setter
    private String emailConfirmationToken;
    @Setter
    private String adminConfirmationToken;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "user_group_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "gp_id"))
    private List<GPModel> groupProjects = new ArrayList<>();


    public void setEmail(String email) {
        this.email = email;
        this.username = email; // Automatically set username to email
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }
    public void setAdminConfirmed(boolean adminConfirmed) {
        isAdminConfirmed = adminConfirmed;
    }

    public boolean isEnabled() {
        if(isEmailConfirmed && isAdminConfirmed){
            return true;
        }
        else {
            return false;
        }
    }

    public void setUserName(String userName) {
        this.username=userName;
    }
    public void setGroupProject(GPModel groupProject) {
        this.groupProjects.add(groupProject);
        groupProject.getMembers().add(this);
    }
}
