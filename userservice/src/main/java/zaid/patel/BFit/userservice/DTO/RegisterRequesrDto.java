package zaid.patel.BFit.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequesrDto {
    private String keycloakId;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Formate")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String firstName;
    private String lastName;
}
