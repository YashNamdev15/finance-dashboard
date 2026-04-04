package com.finance.dashboard.service;

import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;
import com.finance.dashboard.DTO.userUpdate.updateRoleDTO;
import com.finance.dashboard.entity.user.Users;

import java.util.List;

public interface AdminService {

    UserResponseDTO mapToDTO(Users user);
    UserResponseDTO softDeleteUser(Long id);
    UserResponseDTO activateUser(Long id);
    UserResponseDTO updateRole(Long id, updateRoleDTO updateRoleDTO);
    List<UserResponseDTO> getAllUser();
}
