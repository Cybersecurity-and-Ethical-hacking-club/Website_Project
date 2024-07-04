package esc.first.demo.Controllers;

import esc.first.demo.DTO.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/")
    public ResponseEntity<ApiResponseDto<?>> Test() {
        ApiResponseDto<String> response = ApiResponseDto.<String>builder()
                .isSuccess(true)
                .message("Let's learn spring security with JWT!")
                .response(null)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
