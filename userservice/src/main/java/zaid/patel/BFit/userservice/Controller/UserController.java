package zaid.patel.BFit.userservice.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zaid.patel.BFit.userservice.DTO.RegisterRequesrDto;
import zaid.patel.BFit.userservice.DTO.UserResponseDto;
import zaid.patel.BFit.userservice.Service.UserService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequesrDto request){
        return ResponseEntity.ok(userService.register(request));
    }
}
