package com.finance.dashboard.controller.user;

import com.finance.dashboard.DTO.userLoginRegister.LoginReqDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserRequestDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;
import com.finance.dashboard.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("create")
    public ResponseEntity<?> createUser(@Valid
                                        @RequestBody
                                        UserRequestDTO userDto){
        UserResponseDTO user = authService.create(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody
                                   LoginReqDTO loginReqDTO){

        String token = authService.userLogin(loginReqDTO);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
