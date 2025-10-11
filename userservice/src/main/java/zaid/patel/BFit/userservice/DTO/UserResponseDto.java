package zaid.patel.BFit.userservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
