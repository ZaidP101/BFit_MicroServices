package zaid.patel.BFit.gateaway.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Formate")
    private String email;
    private  String keycloakId;
    @NotBlank(message = "Password is required")
    private String password;
    private String firstName;
    private String lastName;
}
