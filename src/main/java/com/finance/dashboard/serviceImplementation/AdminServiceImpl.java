package com.finance.dashboard.serviceImplementation;

import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;
import com.finance.dashboard.DTO.userUpdate.updateRoleDTO;
import com.finance.dashboard.entity.Users;
import com.finance.dashboard.exception.ResourceNotFound;
import com.finance.dashboard.repository.UserRepo;
import com.finance.dashboard.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserResponseDTO softDeleteUser(Long id) {

        Users user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        user.setActive(false);
        Users savedUser = userRepo.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserResponseDTO activateUser(Long id) {

        Users user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        user.setActive(true);
        Users savedUser = userRepo.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateRole(Long id, updateRoleDTO updateRoleDTO) {

        Users user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        user.setRole(updateRoleDTO.getRole());
        Users savedUser = userRepo.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUser() {

        List<UserResponseDTO> users = userRepo.findAll()
                .stream()
                .map(user -> mapToDTO(user))
                .toList();

        return users;
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
