package zaid.patel.BFit.userservice.Service.Implements;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.userservice.DTO.RegisterRequesrDto;
import zaid.patel.BFit.userservice.DTO.UserResponseDto;
import zaid.patel.BFit.userservice.Entity.User;
import zaid.patel.BFit.userservice.Repository.UserRepository;
import zaid.patel.BFit.userservice.Service.UserService;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto register(RegisterRequesrDto request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already exist");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(savedUser.getId());
        userResponseDto.setEmail(savedUser.getEmail());
        userResponseDto.setPassword(savedUser.getPassword());
        userResponseDto.setFirstName(savedUser.getFirstName());
        userResponseDto.setLastName(savedUser.getLastName());
        userResponseDto.setCreatedAt(savedUser.getCreatedAt());
        userResponseDto.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not foumd"));
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUpdatedAt(user.getUpdatedAt());
        return userResponseDto;
    }

    @Override
    public Boolean validateUser(String userId) {
        log.info("Calling User Service for {}", userId);
        return userRepository.existsById(userId);
    }

}
