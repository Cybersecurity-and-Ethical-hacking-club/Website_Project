package esc.first.demo.Services;

import esc.first.demo.DTO.ApiResponseDto;
import esc.first.demo.DTO.SignUpRequestDto;
import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Exceptions.UserAlreadyExistsException;
import esc.first.demo.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserServiceInt {
//    public User registerUser(User user);
    boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;

    public User confirmEmail(String token);
    public User confirmAdmin(Long userId, String token);
    public List<User> findByIsAdminConfirmedFalse();

    List<User> getUnconfirmedUsers();

    List<User> getUsersConfirmedByEmail();

    List<User> getAllUsers();

    List<User> getUsersConfirmedByAdmin();

    boolean deleteUser(Long userId);

    List<User> searchUsersByFirstName(String firstName);

    List<User> searchUsersByLastName(String lastName);

    List<User> searchUsersByEmail(String email);

    void save(User user);
}
