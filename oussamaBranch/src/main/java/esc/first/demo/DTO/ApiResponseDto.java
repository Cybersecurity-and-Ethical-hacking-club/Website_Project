package esc.first.demo.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ApiResponseDto<T> {
    private boolean isSuccess;
    private String message;
    private T response;

    public ApiResponseDto(boolean b, String s) {
    }
}
