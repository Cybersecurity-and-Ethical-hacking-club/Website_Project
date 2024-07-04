package esc.first.demo.Services;

import esc.first.demo.DTO.ApiResponseDto;
import esc.first.demo.DTO.SignUpRequestDto;
import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
}