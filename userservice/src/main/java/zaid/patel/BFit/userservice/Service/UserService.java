package zaid.patel.BFit.userservice.Service;

import zaid.patel.BFit.userservice.DTO.RegisterRequesrDto;
import zaid.patel.BFit.userservice.DTO.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterRequesrDto request);

    UserResponseDto getUser(String userId);

    Boolean validateUser(String userId);
}
