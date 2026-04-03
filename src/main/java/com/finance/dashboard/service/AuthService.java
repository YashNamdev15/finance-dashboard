package com.finance.dashboard.service;

import com.finance.dashboard.DTO.userLoginRegister.LoginReqDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserRequestDTO;
import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;

public interface AuthService {

    UserResponseDTO create(UserRequestDTO userDto);

    String userLogin(LoginReqDTO loginReqDTO);
}
