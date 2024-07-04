package esc.first.demo.Services;

import esc.first.demo.DTO.ApiResponseDto;
import esc.first.demo.DTO.SignUpRequestDto;
import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Exceptions.UserAlreadyExistsException;
import esc.first.demo.Model.User;
import esc.first.demo.Repositpries.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInt {

    private final UserRepo userRepo;
    private final EmailService emailService;
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException {
        logger.info("Received signup request for user: {}", signUpRequestDto.getUserName());

        // Check if user already exists
        if (existsByUsername(signUpRequestDto.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists: " + signUpRequestDto.getUserName());
        }
        if (existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + signUpRequestDto.getEmail());
        }

        // Create User entity
        User user = new User();
        user.setUserName(signUpRequestDto.getUserName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(signUpRequestDto.getPassword());
        user.setRoles(signUpRequestDto.getRoles());

        // Encrypt password, set tokens, and save user
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmailConfirmationToken(UUID.randomUUID().toString());
        user.setAdminConfirmationToken(UUID.randomUUID().toString());

        // Save user to database
        User savedUser = userRepo.save(user);

        // Send confirmation email
        emailService.sendEmail(savedUser.getEmail(),
                "Confirm your registration",
                "To confirm your account, please click here : "
                        +"http://localhost:8080/api/auth/confirm-account?token="+savedUser.getEmailConfirmationToken());

        // Prepare response
        ApiResponseDto<?> responseDto = ApiResponseDto.builder()
                .message("User registered successfully. Check your email for confirmation.")
                .build();

        logger.info("Signup request processed successfully for user: {}", savedUser.getUsername());

        // Return ResponseEntity with the ApiResponseDto
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @Transactional
    public User confirmEmail(String token) {
        logger.info("Attempting to confirm email with token: {}", token);
        Optional<User> userOptional = userRepo.findByEmailConfirmationToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmailConfirmed(true);
            user.setEmailConfirmationToken(null);
            return userRepo.save(user);
        }
        logger.warn("No user found with token: {}", token);
        return null;
    }

    @Override
    @Transactional
    public User confirmAdmin(Long userId, String token) {
        logger.info("Attempting to confirm admin status for user with ID: {}", userId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getAdminConfirmationToken() != null && user.getAdminConfirmationToken().equals(token)) {
                user.setAdminConfirmed(true);
                user.setAdminConfirmationToken(null);
                return userRepo.save(user);
            }
        }
        logger.warn("No user found with ID: {} or token mismatch", userId);
        return null; // User not found or token mismatch
    }

    @Override
    public List<User> findByIsAdminConfirmedFalse() {
        return userRepo.findByIsAdminConfirmedFalse();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getUsersConfirmedByEmail() {
        return userRepo.findByIsEmailConfirmedTrue();
    }

    @Override
    public List<User> getUsersConfirmedByAdmin() {
        return userRepo.findByIsAdminConfirmedTrue();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> searchUsersByFirstName(String firstName) {
        return userRepo.findByFirstNameIgnoreCase(firstName);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public List<User> searchUsersByLastName(String lastName) {
        return userRepo.findByLastNameIgnoreCase(lastName);
    }

    @Override
    public List<User> searchUsersByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public List<User> getUnconfirmedUsers() {
        return userRepo.findByIsAdminConfirmedFalse();
    }
}
