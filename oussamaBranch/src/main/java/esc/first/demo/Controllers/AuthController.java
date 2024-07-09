package esc.first.demo.Controllers;

import esc.first.demo.DTO.ApiResponseDto;
import esc.first.demo.DTO.SignUpRequestDto;
import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Exceptions.UserAlreadyExistsException;
import esc.first.demo.Services.AuthService;
import esc.first.demo.Services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        return userService.signUpUser(signUpRequestDto);
    }


}
