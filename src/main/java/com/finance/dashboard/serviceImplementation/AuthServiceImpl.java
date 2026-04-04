package com.finance.dashboard.serviceImplementation;

import com.finance.dashboard.DTO.userLoginRegister.LoginReqDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserRequestDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;
import com.finance.dashboard.config.JwtUtil;
import com.finance.dashboard.entity.user.Role;
import com.finance.dashboard.entity.user.Users;
import com.finance.dashboard.exception.InvalidArgsException;
import com.finance.dashboard.exception.InvalidCredentials;
import com.finance.dashboard.repository.UserRepo;
import com.finance.dashboard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponseDTO create(UserRequestDTO userDto) {

        Optional<Users> alreadyUser = userRepo.findByEmail(userDto.getEmail());

        if(alreadyUser.isPresent()){
            throw new InvalidCredentials("User with this Email already exist");
        }

        Users user = Users.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .role(Role.VIEWER)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .active(true)
                .build();

        Users savedUser = userRepo.save(user);

        if(savedUser == null){
            throw new InvalidArgsException("Failed to create user");
        }

        return mapToDTO(savedUser);
    }

    @Override
    public String userLogin(LoginReqDTO loginReqDTO) {

        Users user = userRepo.findByEmail(loginReqDTO.getEmail()).orElseThrow(
                () -> new InvalidCredentials("Email is not present in records : " + loginReqDTO.getEmail()));

        if (!passwordEncoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentials("Invalid password");
        }
        return jwtUtil.generateToken(user);
    }

    public UserResponseDTO mapToDTO(Users user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .active(user.getActive())
                .role(user.getRole())
                .build();
    }
}
