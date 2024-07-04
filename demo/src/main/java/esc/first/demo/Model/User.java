package esc.first.demo.Model;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Builder
@Table(name="users")
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Getter
    private String username;



    public void setEmail(String email) {
        this.email = email;
        this.username = email; // Automatically set username to email
    }
    private boolean enabled;
    private boolean isEmailConfirmed;
    private boolean isAdminConfirmed;
    @Setter
    private String emailConfirmationToken;
    @Setter
    private String adminConfirmationToken;
    @CreationTimestamp
    private LocalDateTime CreatedAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;
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
}
