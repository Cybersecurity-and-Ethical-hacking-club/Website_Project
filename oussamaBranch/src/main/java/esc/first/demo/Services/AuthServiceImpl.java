package esc.first.demo.Services;

import esc.first.demo.DTO.ApiResponseDto;
import esc.first.demo.DTO.RoleFactory;
import esc.first.demo.DTO.SignUpRequestDto;
import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Exceptions.UserAlreadyExistsException;
import esc.first.demo.Model.ERole;
import esc.first.demo.Model.Role;
import esc.first.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthServiceImpl implements AuthService {
    @Lazy
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleFactory roleFactory;

    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        if (userService.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
        }
        if (userService.existsByUsername(signUpRequestDto.getUserName())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }

        User user = createUser(signUpRequestDto);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("User account has been successfully created!")
                        .build()
        );
    }

    private User createUser(SignUpRequestDto signUpRequestDto) throws RoleNotFoundException {
        return User.builder()
                .email(signUpRequestDto.getEmail())
                .username(signUpRequestDto.getUserName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .enabled(true)
                .roles(determineRoles(signUpRequestDto.getRoles()))
                .build();
    }

    private Set<Role> determineRoles(Set<Role> strRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(roleFactory.getInstance(ERole.ROLE_MEMBER.toString()));
        } else {
            for (Role role : strRoles) {
                roles.add(roleFactory.getInstance(String.valueOf(role)));
            }
        }
        return roles;
    }
}
